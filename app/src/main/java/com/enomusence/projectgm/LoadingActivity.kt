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
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class LoadingActivity : AppCompatActivity() { //측정버튼 클릭 시 로딩
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

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
}

