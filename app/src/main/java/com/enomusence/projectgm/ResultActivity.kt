package com.enomusence.projectgm

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ResultActivity : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance() // 파이어 스토어 설정
    private lateinit var textView: TextView
    private lateinit var textView1: TextView
    private lateinit var imageView: ImageView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        val Btn = findViewById<Button>(R.id.playlistBtn)
        Btn.setOnClickListener{
            val intent = Intent(this, PlaylistActivity::class.java)
            startActivity(intent)
        }
        imageView = findViewById(R.id.resultImg)
        //결과 보여주기
        textView = findViewById(R.id.resultComent)
        val auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()

        val currentUser = auth.currentUser
        if (currentUser != null) {
            // 현재 사용자의 UID 가져오기
            val uid = currentUser.uid
            //textView1.text = uid  -> 로그인유저 uid 받아오는거 확인

            // 사용자 "memory" 서브컬렉션 참조
            db.collection("User").document(uid)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        // 문서가 존재하면 데이터를 가져와서 TextView에 설정합니다.
                        val data = documentSnapshot.getString("feeling")
                        textView.text = data

                        //감정 데이터 불러오기


                        if(textView.text == "Positive"){
                            imageView.setImageResource(R.drawable.happy)

                        }
                        else if(textView.text == "Neutral"){
                            imageView.setImageResource(R.drawable.neutral)
                        }
                        else if(textView.text == "Negative"){
                            imageView.setImageResource(R.drawable.negative)
                        }
                    } else {
                        textView.text = "No such document"
                    }
                }
                .addOnFailureListener { exception ->
                    textView.text = "Error getting document: $exception"
                }
        }
    }
}