package com.abhijith.myapplication.ui

import android.util.Log
import com.abhijith.myapplication.ui.view.MySimpleExoPlayer
import com.abhijith.myapplication.ui.view.adapters.ViewPager2Adapter

/**
 * Second layer protection for play back conflicts*/
object PlayerManager {
    val listOFCurrentlyPlatingVideos: MutableList<MySimpleExoPlayer> = mutableListOf()

    fun removeSelfAndAbort(player: MySimpleExoPlayer) {
        synchronized(this) {
            listOFCurrentlyPlatingVideos.remove(player)
        }
    }

    fun pauseOther(player: MySimpleExoPlayer){
        synchronized(this){
            listOFCurrentlyPlatingVideos.forEach {
                listOFCurrentlyPlatingVideos.remove(it)
                it.abort()
            }
            listOFCurrentlyPlatingVideos.add(player)
        }
    }

}

object PlayerFlags{
    var isMute:Boolean=false
}