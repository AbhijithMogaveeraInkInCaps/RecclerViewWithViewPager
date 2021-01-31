package com.abhijith.myapplication.ui

import com.abhijith.myapplication.ui.view.MySimpleExoPlayer

var isRecyclerViewIsReady:Boolean = false


/**
 * Second layer protection for play back conflicts*/
object PlayOperations{

    val listOFCurrentlyPlatingVideos:MutableList<MySimpleExoPlayer> = mutableListOf()

    fun removeSelf(player: MySimpleExoPlayer){
        listOFCurrentlyPlatingVideos.remove(player)
    }

    fun pauseOther(player: MySimpleExoPlayer){
        listOFCurrentlyPlatingVideos.forEach {
            it.pause()
            listOFCurrentlyPlatingVideos.remove(it)
        }
        listOFCurrentlyPlatingVideos.add(player)
    }
}