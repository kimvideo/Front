package com.enomusence.projectgm

import android.content.ContentValues.TAG
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.enomusence.projectgm.databinding.ActivityPlaylistBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PlaylistActivity : AppCompatActivity() {

    // firestore 설정
    val fireBaseData = Firebase.firestore

    // binding 선언
    private lateinit var binding: ActivityPlaylistBinding

    // 음악 변수
    lateinit var music : MediaPlayer

    // 음악 재생 관련 버튼
    private lateinit var playBtn : ToggleButton
    lateinit var playNextBtn : ImageButton
    lateinit var playPreviousBtn : ImageButton

    // 노래 앨범 이미지
    private lateinit var cardImg : ImageView
    // 노래 제목과 가수 이름
    private lateinit var songTiltleText : TextView
    private lateinit var singerText : TextView

    // 데이터 배열
    private var fileList = mutableListOf<String>()
    private var fileImgList = mutableListOf<String>()
    private var singerList = mutableListOf<String>()
    private var songsTitleList = mutableListOf<String>()
    private var fileNum:Int = 0

    // tag text
    private lateinit var tagText : TextView

    var flag = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playlist)

        // binding 초기화
        binding = DataBindingUtil.setContentView(this, R.layout.activity_playlist)

        // binding
        cardImg = binding.playMusicImage
        playBtn = binding.playMusic
        playNextBtn = binding.playNextMusic
        playPreviousBtn = binding.playPreviousMusic
        songTiltleText = binding.playMusicTitle
        singerText = binding.playMusicSinger
        tagText = binding.playlistHashtag

        music = MediaPlayer()

        // 홈 화면으로 전환 버튼
        val backHome = Intent(this, MainActivity::class.java)
        val backHomeButton = findViewById<ImageButton>(R.id.backHome)
        backHomeButton.setOnClickListener {
            startActivity(backHome)
        }

        val playlistListView: ListView = findViewById(R.id.playlist)

        // firestore에서 값 가져오기
        // 곡 제목이 너무 긴 경우 앨범 표지가 사이즈가 작아짐
        // firebase에서 감정 값을 받아올 때 그것에 따라 해시태그 변경
        // 앱을 종료했을 때 음악이 꺼지도록

        fireBaseData.collection("HappyMusicDB")
            .get()
            .addOnSuccessListener { result ->
                val allDocuments = result.documents
                val randomUids = mutableSetOf<String>()

                // 20개의 랜덤 UID 가져오기
                while (randomUids.size < 20 && allDocuments.isNotEmpty()) {
                    val randomIndex = (0 until allDocuments.size).random()
                    val randomUid = allDocuments[randomIndex].id

                    // 중복된 UID가 아니라면 추가
                    if (!randomUids.contains(randomUid)) {
                        randomUids.add(randomUid)
                    }

                    allDocuments.removeAt(randomIndex)
                }

                // 중복된 UID를 방지하면서 데이터 추가
                for (uid in randomUids) {
                    val document = result.documents.firstOrNull { it.id == uid }

                    if (document != null) {
                        try {
                            val data = document.data
                            if (data != null) {
                                fileList.add(data["url"] as String)
                                fileImgList.add(data["album_cover"] as String)
                                singerList.add(data["singer"] as String)
                                songsTitleList.add(data["title"] as String)
                            }
                        } catch (e: Exception) {
                            // 예외처리
                            e.printStackTrace()
                        }
                    }
                }

                // ArrayAdapter를 사용하여 ListView에 음악 파일 이름 표시
                val playlistAdapter = PlayListAdapter(this, songsTitleList, fileImgList, singerList)
                playlistListView.adapter = playlistAdapter
            }
            .addOnFailureListener { exception ->
                // 데이터 가져오기 실패 시 예외 처리
                exception.printStackTrace()
            }


        // ListView 아이템 클릭 이벤트 처리
        playlistListView.setOnItemClickListener(AdapterView.OnItemClickListener{ parent, view, position, id ->  
            val selectedIndex = position
            clickPlayListMusic(selectedIndex)
            fileNum = position
        })

        // 현재 재생 중인 음악 클릭
        val nowPlayMusic = findViewById<LinearLayout>(R.id.nowMusic)
        nowPlayMusic.setOnClickListener {
            // 현재 재생 중인 음악의 정보 가져오기
            val url = fileList[fileNum]
            val albumCover = fileImgList[fileNum]
            val singer = singerList[fileNum]
            val title = songsTitleList[fileNum]

            // MusicPlayerBottomSheetDialogFragment에 정보 전달
            val bottomSheetFragment = MusicPlayerBottomSheetDialogFragment()
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
            //bottomSheetFragment.updateMusicInfo(url, albumCover, singer, title)

            // BottomSheetDialogFragment가 표시된 후에 실행되도록 post 함수 사용
            supportFragmentManager.executePendingTransactions()

            // BottomSheetDialogFragment를 확장된 상태로 표시
            val view = bottomSheetFragment.view
            view?.let { v ->
                val parent = v.parent as View
                val params = (parent.layoutParams as CoordinatorLayout.LayoutParams).apply {
                    height = ViewGroup.LayoutParams.MATCH_PARENT
                    bottomMargin = 0
                }
                parent.layoutParams = params
                val behavior = BottomSheetBehavior.from(parent)
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }

        // 하단 바를 처음에 listView를 눌렀을 때 보이도록하는 로직을 추가해야할듯

        // 음악 재생 및 정지 버튼
        playBtn.setOnClickListener {togglePlayMusicButton()}

        // 이전 음악
        playPreviousBtn.setOnClickListener(){

            fileNum--
            // fileNum에 저장된 값이 0보다 작으면 2로
            if(fileNum<0) fileNum = 20

            setData()
        }

        // 다음 음악
        playNextBtn.setOnClickListener(){

            fileNum++
            //fileNum에 저장된 값이 2보다 클 때 0으로
            if(fileNum > 20) fileNum = 0

            setData()
        }

    }

    private fun internet() {
        // 음악 재생
        music = MediaPlayer().apply {
            setAudioStreamType(AudioManager.STREAM_MUSIC)
            setDataSource(fileList[fileNum])
            prepare()
            start()
        }
        Glide.with(this)
            .load(fileImgList[fileNum])
            .into(cardImg)

    }
    private fun clickPlayListMusic(fileIndex: Int) {
        // 현재 재생중인 음악 정지
        music?.stop()
        music.release()

        music = MediaPlayer().apply {
            setAudioStreamType(AudioManager.STREAM_MUSIC)
            setDataSource(fileList[fileIndex])
            prepare()
            start()
        }
        Glide.with(this)
            .load(fileImgList[fileIndex])
            .into(cardImg)

        singerText.text = singerList[fileIndex]
        songTiltleText.text = songsTitleList[fileIndex]

        music.start()
        playBtn.setBackgroundResource(R.drawable.pause_btn)
        flag = true
    }

    fun togglePlayMusicButton(){
        if(flag == false){
            music.start()
            playBtn.setBackgroundResource(R.drawable.pause_btn)
            flag = true
        }
        else{
            music.pause()
            playBtn.setBackgroundResource(R.drawable.play_btn)
            flag = false
        }
    }

    fun setData(){
        music.release()

        // fileNum에 따른 데이터 변경
        internet()
        singerText.text = singerList[fileNum]
        songTiltleText.text = songsTitleList[fileNum]

        music.start()
        playBtn.setBackgroundResource(R.drawable.pause_btn)
        flag = true

    }

}


