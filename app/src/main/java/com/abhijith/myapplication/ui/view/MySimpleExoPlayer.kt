package com.abhijith.myapplication.ui.view

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.util.Log
import com.abhijith.myapplication.R
import com.abhijith.myapplication.ui.PlayerFlags
import com.abhijith.myapplication.ui.PlayerManager
import com.abhijith.myapplication.ui.statemodel.RecyclerViewStateModel
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.RawResourceDataSource
import com.google.android.exoplayer2.util.Util
import kotlin.random.Random

val list = mutableListOf(
    RawResourceDataSource.buildRawResourceUri(R.raw.four),
    RawResourceDataSource.buildRawResourceUri(R.raw.three),
    RawResourceDataSource.buildRawResourceUri(R.raw.two),
    RawResourceDataSource.buildRawResourceUri(R.raw.one),
    RawResourceDataSource.buildRawResourceUri(R.raw.videoplayback),
)

class MySimpleExoPlayer : PlayerView {

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr)

    constructor(context: Context?, attrs: AttributeSet?)
            : super(context, attrs)

    private lateinit var simpleExoPlayer: SimpleExoPlayer

    init {
        keepScreenOn = true
    }

    companion object {
        var position: Long = C.TIME_UNSET
    }

    private fun buildMediaSourceNew(uri: Uri): MediaSource? {
        val datasourceFactroy: DataSource.Factory =
            DefaultDataSourceFactory(context, Util.getUserAgent(context, "myapplication"))
        return ExtractorMediaSource.Factory(datasourceFactroy).createMediaSource(uri)
    }

    fun init(owner:RecyclerViewStateModel.SubViewHolderData) {
        synchronized(this) {
            val loadControl = DefaultLoadControl()
            val bandwidthMeter = DefaultBandwidthMeter()
            val trackSelector = DefaultTrackSelector(AdaptiveTrackSelection.Factory(bandwidthMeter))
            simpleExoPlayer =
                ExoPlayerFactory.newSimpleInstance(context, trackSelector, loadControl)
            player = simpleExoPlayer
            setUri(owner)
        }
    }

    private fun freeMemory() {
        synchronized(this) {
            simpleExoPlayer.stop()
            simpleExoPlayer.release()
            player = null
        }
    }

    private fun setUri(owner:RecyclerViewStateModel.SubViewHolderData) {
        synchronized(this) {
            val num = Random.nextInt(0, 4)
            val mediaSource = buildMediaSourceNew(list[num])
            simpleExoPlayer.playWhenReady = false
            simpleExoPlayer.prepare(mediaSource)
        }
    }

    fun abort() {
        synchronized(this) {
            Log.e("Sukesh", "Playing End")
            if (!isScrollingFast) {
                simpleExoPlayer.playWhenReady = false
                simpleExoPlayer.playbackState
                freeMemory()
            }
        }
    }

    fun play(owner:RecyclerViewStateModel.SubViewHolderData) {

        synchronized(this) {
            if (!isScrollingFast) {
                Log.e("Sukesh", "Playing Start")
                PlayerManager.pauseOther(owner, this)
                init(owner)
                simpleExoPlayer.playWhenReady = true
                simpleExoPlayer.playbackState
                if (PlayerFlags.isMute)
                    mute()
            }
        }
    }

    fun pause(owner:RecyclerViewStateModel.SubViewHolderData) {
        abort()
    }

    fun mute() {
        val curentVol = simpleExoPlayer.volume
        if (!PlayerFlags.isMute) {
            simpleExoPlayer.volume = 1f
        } else {
            simpleExoPlayer.volume = 0f
        }
    }
}