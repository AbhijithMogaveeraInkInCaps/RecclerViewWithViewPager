package com.abhijith.myapplication.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abhijith.myapplication.ui.view.MySimpleExoPlayer

enum class PlayerManagerEvent {
    NEW_PLAYER
}

object PlayerManager {
    val listOFCurrentlyPlatingVideos: MutableList<MySimpleExoPlayer> = mutableListOf()
    val liveData:MutableLiveData<PlayerManagerEvent> = MutableLiveData()

    fun removeSelfAndAbort(player: MySimpleExoPlayer) {
        synchronized(this) {
            listOFCurrentlyPlatingVideos.remove(player)
        }
    }

    fun pauseOther(player: MySimpleExoPlayer){
        liveData.postValue(PlayerManagerEvent.NEW_PLAYER)
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
    var isMuteLiveData:MutableLiveData<Boolean> = MutableLiveData()
}
