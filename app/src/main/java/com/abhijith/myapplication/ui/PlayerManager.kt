package com.abhijith.myapplication.ui

import androidx.lifecycle.MutableLiveData
import com.abhijith.myapplication.ui.data.SubViewHolderData
import com.abhijith.myapplication.ui.view.MySimpleExoPlayer

enum class PlayerManagerEvent {
    NEW_PLAYER
}

object PlayerManager {

    var currentMySimpleExoPlayer: MySimpleExoPlayer? = null
    var currentMySimpleExoPlayerOwnerData: SubViewHolderData? = null
    val liveData: MutableLiveData<PlayerManagerEvent> = MutableLiveData()

    fun pauseOther(owner: SubViewHolderData, player: MySimpleExoPlayer) {
        synchronized(this) {
            currentMySimpleExoPlayer?.pause(currentMySimpleExoPlayerOwnerData!!)
            currentMySimpleExoPlayer = player
            currentMySimpleExoPlayerOwnerData = owner
            liveData.postValue(PlayerManagerEvent.NEW_PLAYER)
        }
    }

}

object PlayerFlags {
    var isMute:Boolean = false
}
