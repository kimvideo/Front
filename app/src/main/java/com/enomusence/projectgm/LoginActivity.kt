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

        val SignUp = Intent(this, SignUpActivity::class.java)
        val SignUpButton = findViewById<Button>(R.id.signUpBtn)
        SignUpButton.setOnClickListener {
            startActivity(SignUp)
        }

        // 로그인 없이 메인화면 이동(개발용)
        val gotoMain = Intent(this,MainActivity::class.java)
        val SkipBtn = findViewById<Button>(R.id.skipBtn)
        SkipBtn.setOnClickListener {
            startActivity(gotoMain)
        }

    }
}