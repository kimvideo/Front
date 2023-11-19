package com.enomusence.projectgm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        /*
        // Login
        val SignUp = Intent(this, SignUpActivity::class.java)
        val SignUpButton = findViewById<Button>(R.id.signUpBtn)
        SignUpButton.setOnClickListener {
            startActivity(SignUp)
        }
        */
        //로그인
        val email = findViewById<EditText>(R.id.loginIdText)
        val password = findViewById<EditText>(R.id.loginPwText)
        val loginBtn = findViewById<Button>(R.id.loginBtn)
        auth = Firebase.auth
        val Main = Intent(this, MainActivity::class.java)

        loginBtn.setOnClickListener {
            auth.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(this, "성공", Toast.LENGTH_LONG).show()
                        startActivity(Main)
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(this, "실패", Toast.LENGTH_LONG).show()
                    }
                }
        }

        //회원가입으로 이동
        val Join = Intent(this, SignUpActivity::class.java)
        val joinButton = findViewById<Button>(R.id.signUpBtn)
        joinButton.setOnClickListener {
            startActivity(Join)
        }

        //로그아웃
        val logoutBtn = findViewById<Button>(R.id.logOutBtn)
        logoutBtn.setOnClickListener {
            Firebase.auth.signOut()
            Toast.makeText(this,"로그아웃 완료", Toast.LENGTH_LONG).show()
        }

        // 로그인 없이 메인화면 이동(개발용)
        val gotoMain = Intent(this,MainActivity::class.java)
        val SkipBtn = findViewById<Button>(R.id.skipBtn)
        SkipBtn.setOnClickListener {
            startActivity(gotoMain)
        }

    }
}