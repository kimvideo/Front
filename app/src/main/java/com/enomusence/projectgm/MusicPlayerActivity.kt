package com.enomusence.projectgm

import android.annotation.SuppressLint
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import android.widget.ToggleButton
import androidx.databinding.DataBindingUtil
import com.enomusence.projectgm.databinding.ActivityMusicPlayerBinding

class MusicPlayerActivity : AppCompatActivity() {

    // binding 선언
    private lateinit var binding: ActivityMusicPlayerBinding

    // 노래 앨범 이미지
    private lateinit var cardImg : ImageView
    // 노래 제목과 가수 이름
    private lateinit var songTiltleText : TextView
    private lateinit var singerText : TextView

    lateinit var music : MediaPlayer

    // 음악 시간 표시
    lateinit var startT : TextView
    lateinit var finishT : TextView

    // Bar
    lateinit var musicBar : SeekBar
    lateinit var volumeBar : SeekBar
    private var totalTime = 0

    // Button
    private lateinit var playBtn : ToggleButton
    lateinit var playNextBtn : ImageButton
    lateinit var playPreviousBtn : ImageButton

    // 배열
    private var fileList = arrayOf(R.raw.only_one, R.raw.wind_and_wish, R.raw.the_song)
    private var fileImgList = arrayOf(R.drawable.only_one_img, R.drawable.wind_and_wish_img, R.drawable.the_song)
    private var singerList = arrayOf("비투비", "BTOB", "비투비(BTOB)")
    private var songsTitleList = arrayOf("너 없인 안 된다", "나의 바람(Wind And Wish)", "노래(The Song)")
    private var fileNum:Int = 0

    var flag = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_player)

        // binding 초기화
        binding = DataBindingUtil.setContentView(this, R.layout.activity_music_player)

        cardImg = binding.musicImg
        playBtn = binding.playAndPause
        startT = binding.startTime
        finishT = binding.finishTime
        musicBar = binding.musicSeekBar
        volumeBar = binding.volume
        playNextBtn = binding.playNext
        playPreviousBtn = binding.playPrevious
        singerText = binding.singer
        songTiltleText = binding.songTitle


        // 음악 데이터 초기 셋팅
        music = MediaPlayer.create(this, fileList[0])
        cardImg.setImageResource(fileImgList[0])
        singerText.text = singerList[0]
        songTiltleText.text = songsTitleList[0]

        // 재생 & 정지 버튼
        playBtn.setOnClickListener {playMusic()}

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

        //음악 반복 재생
        //music.isLooping = true

        setMusic()
        BarHandler()

    }

    fun setMusic(){

        music.setOnPreparedListener {
            //음악의 볼륨을 왼쪽 및 오른쪽 채널 모두에 대해 0.5로 설정
            music.setVolume(0.5f, 0.5f)

            // SeekBar
            //음악의 총 재생 시간을 계산하여 totalTime 변수에 저장
            totalTime = music.duration

            //musicBar의 최대 값은 음악의 전체 재생 시간으로 설정
            musicBar.max = totalTime


            //Music 객체의 setSeekBar 함수를 사용하여 볼륨을 조절하는 SeekBar(volumeBar)에 대한 리스너를 설정
            Music.setSeekBar(volumeBar, music, true)
            //Music 객체의 setSeekBar 함수를 사용하여 음악 재생 진행도를 표시하는 SeekBar(musicBar)에 대한 리스너를 설정
            Music.setSeekBar(musicBar, music, null, true)

            /*val params = music.playbackParams
            params.speed = 1.0f // 배속을 조절하려면 여기에 원하는 배속 값을 넣으세요.
            music.playbackParams = params*/
        }

    }

    fun playMusic(){
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

        setMusic()
        BarHandler()
        music.start()
        playBtn.setBackgroundResource(R.drawable.pause_btn)
        flag = true

    }

    fun BarHandler(){

        val handler = @SuppressLint("HandlerLeak")
        object : Handler() {
            override fun handleMessage(msg: Message) {
                // 메시지에서 현재 음악 위치를 가져옴
                val currentPosition = msg.what
                //  SeekBar(musicBar)의 진행 상황을 현재 음악 위치로 설정
                musicBar.progress = currentPosition
                //현재 음악 위치에 해당하는 시작 시간을 계산
                val startTime = Music.createTimeLable(currentPosition)
                //시작 시간을 화면에 표시하는 TextView(startT)에 설정
                startT.text = startTime
                // 남은 시간을 계산하여 화면에 표시할 형식으로 설정
                //val finishTime = Music.createTimeLable(totalTime - currentPosition)
                val finishTime = Music.createTimeLable(totalTime)
                // 남은 시간을 화면에 표시하는 TextView(finishT)에 설정
                finishT.text = "$finishTime"
            }
        }

        // 스레드 생성
        Thread(Runnable {
            while (true) {
                try {
                    val msg = Message()
                    msg.what = music.currentPosition
                    handler.sendMessage(msg)
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    Log.d("Thread", e.message.toString())
                }
            }
        }).start()
    }



}