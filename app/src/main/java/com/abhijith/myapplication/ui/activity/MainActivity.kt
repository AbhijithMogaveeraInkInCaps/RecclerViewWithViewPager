package com.abhijith.myapplication.ui.activity

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.abhijith.myapplication.R
import com.abhijith.myapplication.ui.PlayerFlags
import com.abhijith.myapplication.ui.view.adapters.RecyclerViewAdapter
import com.abhijith.myapplication.ui.view.adapters.VideoData

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<RecyclerView>(R.id.rv).apply {
            adapter = RecyclerViewAdapter(
                listOf(
                    listOf(
                        VideoData(Uri.parse("")),
                        VideoData(Uri.parse("")),
                    ),
                    listOf(
                        VideoData(Uri.parse("")),
                        VideoData(Uri.parse("")),
                    ),
                    listOf(
                        VideoData(Uri.parse("")),
                        VideoData(Uri.parse("")),
                    ),
                    listOf(
                        VideoData(Uri.parse("")),
                        VideoData(Uri.parse("")),
                    ),
                    listOf(
                        VideoData( Uri.parse("")),
                        VideoData( Uri.parse("")),
                    ),
                    listOf(
                        VideoData(Uri.parse("")),
                        VideoData(Uri.parse("")),
                    )
                )
            )
        }
    }
}