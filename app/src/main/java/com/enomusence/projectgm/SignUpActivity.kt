package com.enomusence.projectgm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val signUpCancel = Intent(this, LoginActivity::class.java)
        val signUpCancelButton = findViewById<Button>(R.id.signUpCancel)
        signUpCancelButton.setOnClickListener {
            startActivity(signUpCancel)
        }
        auth = FirebaseAuth.getInstance()

        val joinBtn = findViewById<Button>(R.id.signUpCheck)
        joinBtn.setOnClickListener {
            val email = findViewById<EditText>(R.id.newId)
            val password = findViewById<EditText>(R.id.newPw)
            val username = findViewById<EditText>(R.id.userName)

            auth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign up success, update UI with the signed-in user's information
                        val user = auth.currentUser

                        // Firebase Authentication을 통해 얻은 UID
                        val userId = user?.uid

                        // Firestore에 저장할 사용자 정보
                        val userMap = hashMapOf(
                            "email" to email.text.toString(),
                            "password" to password.text.toString(),
                            "username" to username.text.toString()
                        )

                        // Firebase Authentication UID를 사용하여 Firestore에 저장
                        val db = FirebaseFirestore.getInstance()
                        db.collection("User")
                            .document(userId!!)
                            .set(userMap)
                            .addOnSuccessListener {
                                Log.d("JoinActivity", "DocumentSnapshot added with ID: $userId")
                                Toast.makeText(this, "회원가입 성공", Toast.LENGTH_LONG).show()
                            }
                            .addOnFailureListener { e ->
                                Log.w("JoinActivity", "Error adding document", e)
                                Toast.makeText(this, "회원가입 실패", Toast.LENGTH_LONG).show()
                            }
                    } else {
                        // If sign up fails, display a message to the user.
                        Toast.makeText(this, "회원가입 실패", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
}