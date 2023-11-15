package com.enomusence.projectgm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val signUpCancel = Intent(this, LoginActivity::class.java)
        val signUpCancelButton = findViewById<Button>(R.id.signUpCancel)
        signUpCancelButton.setOnClickListener {
            startActivity(signUpCancel)
        }
    }
}