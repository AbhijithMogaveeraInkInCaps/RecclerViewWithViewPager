package com.abhijith.myapplication.ui

import androidx.lifecycle.MutableLiveData
import com.abhijith.myapplication.ui.statemodel.RecyclerViewStateModel
import com.abhijith.myapplication.ui.view.MySimpleExoPlayer
import java.util.*

enum class PlayerManagerEvent {
    NEW_PLAYER
}

object PlayerManager {
    var currentMySimpleExoPlayer: MySimpleExoPlayer? = null
    private var currentMySimpleExoPlayerOwnerData: RecyclerViewStateModel.SubViewHolderData? = null
    val liveData: MutableLiveData<PlayerManagerEvent> = MutableLiveData()

    fun pauseOther(owner: RecyclerViewStateModel.SubViewHolderData,player: MySimpleExoPlayer) {
        liveData.postValue(PlayerManagerEvent.NEW_PLAYER)
        synchronized(this) {
            currentMySimpleExoPlayer?.pause(currentMySimpleExoPlayerOwnerData!!)
            currentMySimpleExoPlayer = player
            currentMySimpleExoPlayerOwnerData = owner
        }
    }
}

object PlayerFlags {
    var isMute:Boolean = false
    var isMuteLiveData: MutableLiveData<Boolean> = MutableLiveData()
}
