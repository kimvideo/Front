package com.enomusence.projectgm

import android.content.Context
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Random

class LoadingActivity : AppCompatActivity() { //측정버튼 클릭 시 로딩

    private val db = FirebaseFirestore.getInstance() //파이어베이스 -> 파이어 스토어 설정
    private lateinit var  textView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        textView = findViewById(R.id.todaysSomething)  //오늘의 한마디 값을 텍스트뷰에 받아옴
        //파이어 베이스에서 데이터 읽어오기
        //readDataFromFirestore()
        getRandomDocumentFromFirestore()

        if(isConnectInternet() != "null"){ //인터넷 연결 성공
            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed({
                val intent = Intent(baseContext, ResultActivity::class.java)
                startActivity(intent)

            },5000)
        }
        else{  //인터넷 연결 실패-> 메인화면으로 돌아가기
            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed({
                val intent = Intent(baseContext, RecyclerView::class.java)
                Toast.makeText(this, "측정오류", Toast.LENGTH_SHORT).show()
                startActivity(intent)
                finish()
            },3000)
        }
    }
    private fun isConnectInternet() : String{ //연결유무 확인
        val cm : ConnectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = cm.activeNetworkInfo
        return networkInfo.toString()
    }
    private fun readDataFromFirestore(){
        val docRef = db.collection("Advices").document("bCjlQ0BT3B5wQFMIHogj")

        docRef.get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    // 문서가 존재하면 데이터를 가져와서 TextView에 설정합니다.
                    val data = documentSnapshot.getString("content")
                    textView.text = data
                } else {
                    textView.text = "No such document"
                }
            }
            .addOnFailureListener { exception ->
                textView.text = "Error getting document: $exception"
            }
    }
    private fun getRandomDocumentFromFirestore() {
        db.collection("Advices").get().addOnSuccessListener { querySnapshot ->
            if (querySnapshot.isEmpty) {
                textView.text = "No documents found"
                return@addOnSuccessListener
            }
            // 랜덤한 문서 ID 선택
            val randomDocumentId = getRandomDocumentId(querySnapshot.documents.size)
            val randomIndex = Random().nextInt(querySnapshot.size())
            val randomDocument = querySnapshot.documents[randomIndex]

            // 선택한 문서의 데이터 가져와서 TextView에 설정
            val data = randomDocument.getString("content")
            if (data != null) {
                textView.text = data
            } else {
                textView.text = "No such document"
            }
        }
            .addOnFailureListener { exception ->
                textView.text = "Error getting documents: $exception"
            }
    }
    private fun getRandomDocumentId(max: Int): String? {
        // 0부터 max까지의 랜덤한 정수 생성
        val random = Random()
        val randomIndex = random.nextInt(max)
        // QuerySnapshot에서 랜덤한 문서 ID 가져오기
        return if (max > 0) {
            return random.nextInt(max).toString()
        } else {
            null
        }
    }
}

