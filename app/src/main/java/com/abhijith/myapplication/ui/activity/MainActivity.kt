package com.abhijith.myapplication.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.abhijith.myapplication.R
import com.abhijith.myapplication.ui.recyclerview.adapter.PostAdapterRV
import com.abhijith.myapplication.ui.viewpager.adapter.PostContentAdapterVP

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.elevation = 0f
        findViewById<RecyclerView>(R.id.rv).apply {
            adapter = PostAdapterRV(this@MainActivity)
        }
    }
}