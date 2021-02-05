package com.abhijith.myapplication.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.abhijith.myapplication.R
import com.abhijith.myapplication.ui.PlayerManager
import com.abhijith.myapplication.ui.statemodel.RecyclerViewStateModel
import com.abhijith.myapplication.ui.view.adapters.RecyclerViewAdapter

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
        PlayerManager.currentMySimpleExoPlayer?.pause(PlayerManager.currentMySimpleExoPlayerOwnerData!!)
    }

    override fun onResume() {
        super.onResume()
        PlayerManager.currentMySimpleExoPlayer?.play(PlayerManager.currentMySimpleExoPlayerOwnerData!!)
    }
}