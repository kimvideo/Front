package com.enomusence.projectgm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    lateinit var imogeRecyclerView1 : RecyclerView
    lateinit var tvDate : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setContentView(R.layout.activity_main)

        imogeRecyclerView1 = findViewById(R.id.imogerecyclerHorizon)


        //버튼 눌렀을때 측정 로딩으로 넘어감
        val Loading = Intent(this, LoadingActivity::class.java)
        val L_Button = findViewById<Button>(R.id.measure_Button)
        L_Button.setOnClickListener {
            startActivity(Loading)
        }
        val play = Intent(this, PlaylistActivity::class.java)
        val P_Button = findViewById<Button>(R.id.playlist_Button)
        P_Button.setOnClickListener {
            startActivity(play)
        }
        //val binding = ActivityMainBinding.inflate(layoutInflater)
        // 로딩 애니메이션, 강현이 플레이리스트 레이아웃 연결하기
    }
}