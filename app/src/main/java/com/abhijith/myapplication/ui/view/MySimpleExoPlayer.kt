package com.abhijith.myapplication.ui.view

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import com.abhijith.myapplication.R
import com.abhijith.myapplication.ui.PlayerFlags
import com.abhijith.myapplication.ui.PlayerManager
import com.abhijith.myapplication.ui.data.SubViewHolderData
import com.abhijith.myapplication.ui.view.RecyclerViewPostContainer.Companion.isJustStarted
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
    var isThereAnyNeedToReInit: Boolean = true

    init {
        keepScreenOn = true
    }

    companion object {
        var position: Long = C.TIME_UNSET
    }

    private fun buildMediaSourceNew(uri: Uri): MediaSource? {
        val dataSourceFactory: DataSource.Factory =
            DefaultDataSourceFactory(context, Util.getUserAgent(context, "myapplication"))
        return ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(uri)
    }

    private fun init(owner: SubViewHolderData) {
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

    private fun setUri(owner: SubViewHolderData) {
        synchronized(this) {
            val num = Random.nextInt(0, 4)
            val mediaSource = buildMediaSourceNew(list[num])
            simpleExoPlayer.playWhenReady = false
            simpleExoPlayer.prepare(mediaSource)
        }
    }

    fun abort(owner: SubViewHolderData) {
        synchronized(this) {
            if (!isScrollingFast || isJustStarted) {
                if (!isThereAnyNeedToReInit) {
                    simpleExoPlayer.playWhenReady = false
                    simpleExoPlayer.playbackState
                }
            }
        }
    }

    fun play(owner: SubViewHolderData) {
        synchronized(this) {
            if (!isScrollingFast || isJustStarted) {
                PlayerManager.pauseOther(owner, this)
                if (isThereAnyNeedToReInit) {
                    isThereAnyNeedToReInit = false
                    init(owner)
                }
                simpleExoPlayer.playWhenReady = true
                simpleExoPlayer.playbackState
                if (PlayerFlags.isMute)
                    mute()
            }
        }
    }

    fun pause(owner: SubViewHolderData) {
        abort(owner)
    }

    fun mute() {
        if (!PlayerFlags.isMute) {
            simpleExoPlayer.volume = 1f
        } else {
            simpleExoPlayer.volume = 0f
        }
    }

    fun allocateMemoryAndBeReady(owner: SubViewHolderData) {
        if (isThereAnyNeedToReInit) {
            isThereAnyNeedToReInit = false
            init(owner)
            simpleExoPlayer.seekTo(1)
        }
    }
}