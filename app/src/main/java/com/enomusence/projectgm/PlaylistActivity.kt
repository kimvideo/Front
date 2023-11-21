package com.enomusence.projectgm

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.databinding.DataBindingUtil
import com.enomusence.projectgm.databinding.ActivityPlaylistBinding


class PlaylistActivity : AppCompatActivity() {

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
    private var fileList = arrayOf(R.raw.only_one, R.raw.wind_and_wish, R.raw.the_song)
    private var fileImgList = arrayOf(R.drawable.only_one_img, R.drawable.wind_and_wish_img, R.drawable.the_song)
    private var singerList = arrayOf("비투비", "BTOB", "비투비(BTOB)")
    private var songsTitleList = arrayOf("너 없인 안 된다", "나의 바람", "노래(The Song)")
    private var fileNum:Int = 0

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

        // 홈 화면으로 전환 버튼
        val backHome = Intent(this, MainActivity::class.java)
        val backHomeButton = findViewById<ImageButton>(R.id.backHome)
        backHomeButton.setOnClickListener {
            startActivity(backHome)
        }

        val playlistListView: ListView = findViewById(R.id.playlist)

        // ArrayAdapter를 사용하여 ListView에 음악 파일 이름 표시
        val playlistAdapter = PlayListAdapter(this, songsTitleList, fileImgList, singerList)
        playlistListView.adapter = playlistAdapter


        // ListView 아이템 클릭 이벤트 처리
        playlistListView.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            val selectedIndex = position
            clickPlayListMusic(selectedIndex)
        })

        val nowPlay = Intent(this, MusicPlayerActivity::class.java)
        val nowPlayMusic = findViewById<LinearLayout>(R.id.nowMusic)
        nowPlayMusic.setOnClickListener {
            startActivity(nowPlay)
        }

        // 음악 데이터 초기 셋팅
        music = MediaPlayer.create(this, fileList[0])
        cardImg.setImageResource(fileImgList[0])
        singerText.text = singerList[0]
        songTiltleText.text = songsTitleList[0]

        // 음악 재생 및 정지 버튼
        playBtn.setOnClickListener {togglePlayMusicButton()}

        // 이전 음악
        playPreviousBtn.setOnClickListener(){

            fileNum--
            // fileNum에 저장된 값이 0보다 작으면 2로
            if(fileNum<0) fileNum = 2

            setData()
        }

        // 다음 음악
        playNextBtn.setOnClickListener(){

            fileNum++
            //fileNum에 저장된 값이 2보다 클 때 0으로
            if(fileNum > 2) fileNum = 0

            setData()
        }

    }
    private fun clickPlayListMusic(fileIndex: Int) {
        // 현재 재생중인 음악 정지
        music?.stop()
        music.release()

        music = MediaPlayer.create(this,fileList[fileIndex])
        cardImg.setImageResource(fileImgList[fileIndex])
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
        music = MediaPlayer.create(this,fileList[fileNum])
        cardImg.setImageResource(fileImgList[fileNum])
        singerText.text = singerList[fileNum]
        songTiltleText.text = songsTitleList[fileNum]

        music.start()
        playBtn.setBackgroundResource(R.drawable.pause_btn)
        flag = true

    }

}


