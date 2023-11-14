package com.enomusence.projectgm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout

class PlaylistActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playlist)

        val nowPlay = Intent(this, MusicPlayerActivity::class.java)
        val nowPlayMusic = findViewById<LinearLayout>(R.id.nowMusic)
        nowPlayMusic.setOnClickListener {
            startActivity(nowPlay)
        }
    }
}