package com.enomusence.projectgm

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ResultActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        val Btn = findViewById<Button>(R.id.playlistBtn)
        Btn.setOnClickListener({
            val intent = Intent(this, PlaylistActivity::class.java)
            startActivity(intent)
        })
    }
}