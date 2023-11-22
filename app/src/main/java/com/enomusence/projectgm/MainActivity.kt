package com.enomusence.projectgm

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.enomusence.projectgm.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var  binding : ActivityMainBinding
    lateinit var imogeRecyclerView1 : RecyclerView
    lateinit var navigationView: NavigationView
    lateinit var drawerLayout: DrawerLayout
    private lateinit var selectedDataTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //여기부터 스토리뷰에 들어갈 코드
        val storyBoard = findViewById<RecyclerView>(R.id.imogerecycler)
        val itemList = ArrayList<friendData>()
       // itemList.add(friendData("test1","화남"))


        val storyAdapter =storyAdapter(itemList)
        storyAdapter.notifyDataSetChanged()
        storyBoard.adapter = storyAdapter
        storyBoard.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


        //버튼 바인딩
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.playlistButton.setOnClickListener{   // 플레이리스트 버튼
            val intent = Intent(this, PlaylistActivity::class.java)
            startActivity(intent)
        }
        binding.measureButton.setOnClickListener{  //측청 버튼
            val intent = Intent(this,LoadingActivity::class.java)
            startActivity(intent)
        }

        //사이드바 구현
        val toolbar: Toolbar = findViewById(R.id.toolbar) // toolBar를 통해 App Bar 생성
        setSupportActionBar(toolbar) // 툴바 적용
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // 드로어를 꺼낼 홈 버튼 활성화
        supportActionBar?.setHomeAsUpIndicator(R.drawable.navi_menu) // 홈버튼 이미지 변경
        supportActionBar?.setDisplayShowTitleEnabled(false) // 툴바에 타이틀 안보이게

        // 네비게이션 드로어 생성
        drawerLayout = findViewById(R.id.drawer_layout)

        // 네비게이션 드로어 내에있는 화면의 이벤트를 처리하기 위해 생성
        navigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this) //navigation 리스너
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        // 클릭한 툴바 메뉴 아이템 id 마다 다르게 실행하도록 설정
        when(item!!.itemId){
            android.R.id.home->{
                // 메뉴 버튼 클릭시 네비게이션 드로어 열기
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_item1-> Toast.makeText(this,"menu_item1 실행",Toast.LENGTH_SHORT).show()
            R.id.menu_item2-> Toast.makeText(this,"menu_item2 실행",Toast.LENGTH_SHORT).show()
            R.id.menu_item3-> Toast.makeText(this,"menu_item3 실행",Toast.LENGTH_SHORT).show()
        }
        return false
    }
}