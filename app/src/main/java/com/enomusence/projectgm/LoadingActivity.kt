package com.enomusence.projectgm

import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class LoadingActivity : AppCompatActivity() {
    private lateinit var LoadingAnimation: AnimationDrawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        val rocketImage = findViewById<ImageView>(R.id.imageView).apply {
            setBackgroundResource(R.drawable.loading)
            LoadingAnimation = background as AnimationDrawable
        }
        rocketImage.setOnClickListener({ LoadingAnimation.start() })

    }
}