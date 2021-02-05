package com.abhijith.myapplication.ui.view

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.util.Log
import com.abhijith.myapplication.R
import com.abhijith.myapplication.ui.PlayerFlags
import com.abhijith.myapplication.ui.PlayerManager
import com.abhijith.myapplication.ui.statemodel.RecyclerViewStateModel
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

    private lateinit var simpleExoPlayer: SimpleExoPlayer
    var isThereAnyNeedToReInit:Boolean=true

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

    private fun init(owner: RecyclerViewStateModel.SubViewHolderData) {
        synchronized(this) {
            val loadControl = DefaultLoadControl()
            val bandwidthMeter = DefaultBandwidthMeter()
            val trackSelector =
                DefaultTrackSelector(AdaptiveTrackSelection.Factory(bandwidthMeter))
            simpleExoPlayer =
                ExoPlayerFactory.newSimpleInstance(context, trackSelector, loadControl)
            player = simpleExoPlayer
            setUri(owner)
            simpleExoPlayer.addListener(object : Player.EventListener {
                override fun onTimelineChanged(
                    timeline: Timeline?,
                    manifest: Any?,
                    reason: Int
                ) {

                }

                override fun onTracksChanged(
                    trackGroups: TrackGroupArray?,
                    trackSelections: TrackSelectionArray?
                ) {

                }

                override fun onLoadingChanged(isLoading: Boolean) {

                }

                override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {

                }

                override fun onRepeatModeChanged(repeatMode: Int) {
                }

                override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
                }

                override fun onPlayerError(error: ExoPlaybackException?) {
                    freeMemory()
                }

                override fun onPositionDiscontinuity(reason: Int) {
                }

                override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters?) {
                }

                override fun onSeekProcessed() {
                }
            })
//            }
        }
    }

    fun freeMemory() {
        synchronized(this) {
            if (!isThereAnyNeedToReInit) {
                simpleExoPlayer.stop()
                simpleExoPlayer.release()
                player = null
                isThereAnyNeedToReInit = true
            }
        }
    }

    private fun setUri(owner: RecyclerViewStateModel.SubViewHolderData) {
        synchronized(this) {
            val num = Random.nextInt(0, 4)
            val mediaSource = buildMediaSourceNew(list[num])
            simpleExoPlayer.playWhenReady = false
            simpleExoPlayer.prepare(mediaSource)
        }
    }

    fun abort(owner: RecyclerViewStateModel.SubViewHolderData) {
        synchronized(this) {
            if (!isScrollingFast) {
                if (!isThereAnyNeedToReInit) {
//                    owner.saveLastLocation(simpleExoPlayer.contentPosition)
                    simpleExoPlayer.playWhenReady = false
                    simpleExoPlayer.playbackState
//                    freeMemory()
                }
            }
        }
    }

    fun play(owner: RecyclerViewStateModel.SubViewHolderData) {
        synchronized(this) {
            if (!isScrollingFast) {
                PlayerManager.pauseOther(owner, this)
                if (isThereAnyNeedToReInit) {
                    isThereAnyNeedToReInit = false
                    init(owner)
                }
                simpleExoPlayer.playWhenReady = true
                simpleExoPlayer.playbackState
//                if (owner.getLastPlayedLocation() != C.TIME_UNSET) {
//                    simpleExoPlayer.seekTo(owner.getLastPlayedLocation())
//                }
                if (PlayerFlags.isMute)
                    mute()
            }
        }
    }

    fun pause(owner: RecyclerViewStateModel.SubViewHolderData) {
        abort(owner)
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