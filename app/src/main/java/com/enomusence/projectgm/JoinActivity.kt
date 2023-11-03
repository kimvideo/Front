package com.enomusence.projectgm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class JoinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        val JoinCancel = Intent(this, LoginActivity::class.java)
        val joinCancelButton = findViewById<Button>(R.id.joinCancel)
        joinCancelButton.setOnClickListener {
            startActivity(JoinCancel)
        }

    }
}