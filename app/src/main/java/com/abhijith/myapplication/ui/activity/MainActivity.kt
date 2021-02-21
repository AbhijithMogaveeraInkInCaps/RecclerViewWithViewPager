package com.abhijith.myapplication.ui.activity

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.abhijith.myapplication.R
import com.abhijith.myapplication.ui.PlayerManager
import com.abhijith.myapplication.ui.data.DataType
import com.abhijith.myapplication.ui.data.SubViewHolderData
import com.abhijith.myapplication.ui.data.ViewHolderData
import com.abhijith.myapplication.ui.view.RecyclerViewPostContainer.Companion.isJustStarted
import com.abhijith.myapplication.ui.view.adapters.RecyclerViewAdapter

class MainActivity : AppCompatActivity() {
    private val dataList = listOf(
        ViewHolderData(
            listOf(
                SubViewHolderData(Uri.parse(""), 0, DataType.VIDEO),
                SubViewHolderData(Uri.parse(""), 1, DataType.VIDEO),
            ), 0
        ),
        ViewHolderData(
            listOf(
                SubViewHolderData(Uri.parse(""), 2, DataType.VIDEO),
                SubViewHolderData(Uri.parse(""), 3, DataType.VIDEO),
            ), 1
        ),
        ViewHolderData(
            listOf(
                SubViewHolderData(Uri.parse(""), 4, DataType.VIDEO),
                SubViewHolderData(Uri.parse(""), 5, DataType.VIDEO),
            ), 2
        ),
        ViewHolderData(
            listOf(
                SubViewHolderData(Uri.parse(""), 6, DataType.VIDEO),
                SubViewHolderData(Uri.parse(""), 7, DataType.VIDEO),
            ), 3
        ),
        ViewHolderData(
            listOf(
                SubViewHolderData(Uri.parse(""), 8, DataType.VIDEO),
                SubViewHolderData(Uri.parse(""), 9, DataType.VIDEO),
            ), 4
        ),
        ViewHolderData(
            listOf(
                SubViewHolderData(Uri.parse(""), 10, DataType.VIDEO),
                SubViewHolderData(Uri.parse(""), 11, DataType.VIDEO),
            ), 5
        ),
        ViewHolderData(
            listOf(
                SubViewHolderData(Uri.parse(""), 12, DataType.VIDEO),
                SubViewHolderData(Uri.parse(""), 13, DataType.VIDEO),
            ), 6
        ),
        ViewHolderData(
            listOf(
                SubViewHolderData(Uri.parse(""), 14, DataType.VIDEO),
                SubViewHolderData(Uri.parse(""), 15, DataType.VIDEO),
            ), 7
        ),
        ViewHolderData(
            listOf(
                SubViewHolderData(Uri.parse(""), 16, DataType.VIDEO),
                SubViewHolderData(Uri.parse(""), 17, DataType.VIDEO),
            ), 8
        ),
        ViewHolderData(
            listOf(
                SubViewHolderData(Uri.parse(""), 17, DataType.VIDEO),
                SubViewHolderData(Uri.parse(""), 18, DataType.VIDEO),
            ), 9
        ),
        ViewHolderData(
            listOf(
                SubViewHolderData(Uri.parse(""), 19, DataType.VIDEO),
                SubViewHolderData(Uri.parse(""), 20, DataType.VIDEO),
            ), 10
        ),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<RecyclerView>(R.id.rv).apply {
            adapter = RecyclerViewAdapter(dataList)
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