package com.abhijith.myapplication.ui

import androidx.lifecycle.MutableLiveData
import com.abhijith.myapplication.ui.view.MySimpleExoPlayer
import com.abhijith.myapplication.ui.view.adapters.VideoData
import com.google.android.exoplayer2.C

enum class PlayerManagerEvent {
    NEW_PLAYER
}

object PlayerManager {
    var currentMySimpleExoPlayer: MySimpleExoPlayer? = null
    var currentPlayerVideoData:VideoData?=null
    val liveData: MutableLiveData<PlayerManagerEvent> = MutableLiveData()

    fun pauseOther(videoData: VideoData,player: MySimpleExoPlayer) {
        liveData.postValue(PlayerManagerEvent.NEW_PLAYER)
        synchronized(this) {
            currentMySimpleExoPlayer?.pause(currentPlayerVideoData!!)
            currentMySimpleExoPlayer = player
            currentPlayerVideoData = videoData
        }
    }
}

object PlayerFlags {
    var isMute:Boolean = false
    var isMuteLiveData: MutableLiveData<Boolean> = MutableLiveData()

    var preVideosVideoLocation = C.TIME_UNSET
    var currentVideosVideoLocation = C.TIME_UNSET
    var nextVideosVideoLocation = C.TIME_UNSET
}
