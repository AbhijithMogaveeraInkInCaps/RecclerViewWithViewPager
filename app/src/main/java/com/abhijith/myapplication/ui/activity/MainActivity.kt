package com.abhijith.myapplication.ui.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.abhijith.myapplication.R
import com.abhijith.myapplication.ui.PlayerManager
import com.abhijith.myapplication.ui.statemodel.RecyclerViewStateModel
import com.abhijith.myapplication.ui.view.RecyclerViewPostContainer.Companion.isJustStarted
import com.abhijith.myapplication.ui.view.adapters.RecyclerViewAdapter
import com.abhijith.myapplication.ui.view.isScrollingFast
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        RecyclerViewStateModel.applicationContext = this.applicationContext
        findViewById<RecyclerView>(R.id.rv).apply {
            adapter = RecyclerViewAdapter(RecyclerViewStateModel)
        }

    }


    override fun onPause() {
        super.onPause()
        isJustStarted = true
        PlayerManager
            .currentMySimpleExoPlayer
            ?.pause(PlayerManager.currentMySimpleExoPlayerOwnerData!!)
    }

    override fun onResume() {
        super.onResume()
        PlayerManager
            .currentMySimpleExoPlayer
            ?.play(PlayerManager.currentMySimpleExoPlayerOwnerData!!)
    }
}