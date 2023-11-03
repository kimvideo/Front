package com.enomusence.projectgm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Login

        val Join = Intent(this, JoinActivity::class.java)
        val joinButton = findViewById<Button>(R.id.joinBtn)
        joinButton.setOnClickListener {
            startActivity(Join)
        }

    }
}