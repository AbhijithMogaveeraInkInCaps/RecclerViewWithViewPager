package com.abhijith.myapplication.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.abhijith.myapplication.R
import com.abhijith.myapplication.ui.PlayerFlags
import com.abhijith.myapplication.ui.view.adapters.RecyclerViewAdapter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PlayerFlags.isMuteLiveData.postValue(false)
        setContentView(R.layout.activity_main)
        findViewById<RecyclerView>(R.id.rv).apply {
            adapter = RecyclerViewAdapter()
        }
    }
}