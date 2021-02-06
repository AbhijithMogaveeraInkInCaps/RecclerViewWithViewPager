package com.abhijith.myapplication.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.abhijith.myapplication.ui.statemodel.RecyclerViewStateModel
import com.abhijith.myapplication.ui.view.MySimpleExoPlayer
import java.util.*

enum class PlayerManagerEvent {
    NEW_PLAYER
}

object PlayerManager {

    var currentMySimpleExoPlayer: MySimpleExoPlayer? = null
    var currentMySimpleExoPlayerOwnerData: RecyclerViewStateModel.SubViewHolderData? = null
    val liveData: MutableLiveData<PlayerManagerEvent> = MutableLiveData()

    fun pauseOther(owner: RecyclerViewStateModel.SubViewHolderData,player: MySimpleExoPlayer) {
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
