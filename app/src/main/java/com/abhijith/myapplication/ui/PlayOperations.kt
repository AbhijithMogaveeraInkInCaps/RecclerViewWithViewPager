package com.abhijith.myapplication.ui

import com.abhijith.myapplication.ui.view.MySimpleExoPlayer

var isRecyclerViewIsReady:Boolean = false

val listOFCurrentlyPlatingVideos:MutableList<MySimpleExoPlayer> = mutableListOf()

object PlayOperations{

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