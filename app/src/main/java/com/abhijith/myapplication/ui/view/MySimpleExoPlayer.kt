package com.abhijith.myapplication.ui.view

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.util.Log
import com.abhijith.myapplication.R
import com.abhijith.myapplication.ui.PlayerFlags
import com.abhijith.myapplication.ui.PlayerManager
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
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

    constructor(context: Context?, color: Int) : super(context) {

    }

    private lateinit var simpleExoPlayer: SimpleExoPlayer

    init {
        keepScreenOn = true
    }

    private fun buildMediaSourceNew(uri: Uri): MediaSource? {
        val datasourceFactroy: DataSource.Factory =
            DefaultDataSourceFactory(context, Util.getUserAgent(context, "myapplication"))
        return ExtractorMediaSource.Factory(datasourceFactroy).createMediaSource(uri)
    }

    fun init(uri: Uri) {
        synchronized(this) {
            val loadControl: DefaultLoadControl = DefaultLoadControl()
            val bandwidthMeter: DefaultBandwidthMeter = DefaultBandwidthMeter()
            val trackSelector = DefaultTrackSelector(AdaptiveTrackSelection.Factory(bandwidthMeter))
            simpleExoPlayer =
                ExoPlayerFactory.newSimpleInstance(context, trackSelector, loadControl)
            player = simpleExoPlayer
            setUri(Uri.parse(""))
        }
    }

    private fun freeMemory() {
        synchronized(this) {
            simpleExoPlayer.stop()
            simpleExoPlayer.release()
        }
    }

    private fun setUri(uri: Uri) {
        synchronized(this) {
            val num = Random.nextInt(0, 4)
            val mediaSource = buildMediaSourceNew(list[num])
            simpleExoPlayer.playWhenReady = false
            simpleExoPlayer.prepare(mediaSource)
            simpleExoPlayer.addListener(object : Player.EventListener {
                override fun onTimelineChanged(timeline: Timeline?, manifest: Any?, reason: Int) {
                }

                override fun onTracksChanged(
                    trackGroups: TrackGroupArray?,
                    trackSelections: TrackSelectionArray?
                ) {
                    Log.e("ExoPlayer","onTracksChanged")
                }

                override fun onLoadingChanged(isLoading: Boolean) {
                    Log.e("ExoPlayer","onLoadingChanged")
                }

                override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                    Log.e("ExoPlayer","onPlayerStateChanged")
                }

                override fun onRepeatModeChanged(repeatMode: Int) {
                    Log.e("ExoPlayer","onRepeatModeChanged")
                }

                override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
                    Log.e("ExoPlayer","onShuffleModeEnabledChanged")
                }

                override fun onPlayerError(error: ExoPlaybackException?) {
                    Log.e("ExoPlayer","onPlayerError")
                }

                override fun onPositionDiscontinuity(reason: Int) {
                    Log.e("ExoPlayer","onPositionDiscontinuity")
                }

                override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters?) {
                    Log.e("ExoPlayer","onPlaybackParametersChanged")
                }

                override fun onSeekProcessed() {
                    Log.e("ExoPlayer","onSeekProcessed")
                }

            })
        }
    }

    fun abort() {
        synchronized(this) {
            simpleExoPlayer.playWhenReady = false
            simpleExoPlayer.playbackState
            PlayerManager.removeSelfAndAbort(this)
            freeMemory()
        }
    }

    fun play() {
        synchronized(this) {
            PlayerManager.pauseOther(this)
            init(Uri.parse(""))
            simpleExoPlayer.playWhenReady = true
            simpleExoPlayer.playbackState
            if(PlayerFlags.isMute)
                mute()
        }
    }

    fun pause() {
        simpleExoPlayer.playWhenReady = false
        simpleExoPlayer.playbackState
        PlayerManager.removeSelfAndAbort(this)
        freeMemory()
    }

    fun mute():Boolean {
        val curentVol = simpleExoPlayer.volume
        if (curentVol == 0f) {
            simpleExoPlayer.volume = 1f
            return true
        } else {
            simpleExoPlayer.volume = 0f
            return false
        }
    }
}