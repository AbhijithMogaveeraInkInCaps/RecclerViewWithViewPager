package com.abhijith.myapplication.ui.view

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.abhijith.myapplication.R
import com.abhijith.myapplication.ui.PlayOperations
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.extractor.ExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.*
import com.google.android.exoplayer2.util.Util


class MySimpleExoPlayer : PlayerView, LifecycleObserver {

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr)

    constructor(context: Context?, attrs: AttributeSet?)
            : super(context, attrs)

    constructor(context: Context?, color: Int) : super(context) {

    }

    lateinit var loadControl:DefaultLoadControl
    lateinit var bandwidthMeter:DefaultBandwidthMeter
    lateinit var trackSelector:DefaultTrackSelector
    lateinit var simpleExoPlayer: SimpleExoPlayer

    private fun buildMediaSourceNew(uri: Uri): MediaSource? {
        val datasourceFactroy: DataSource.Factory =
            DefaultDataSourceFactory(context, Util.getUserAgent(context, "myapplication"))
        return ExtractorMediaSource.Factory(datasourceFactroy).createMediaSource(uri)
    }
    fun setUri(uri: Uri) {
        loadControl = DefaultLoadControl()
        bandwidthMeter= DefaultBandwidthMeter()
        trackSelector = DefaultTrackSelector(AdaptiveTrackSelection.Factory(bandwidthMeter))
        simpleExoPlayer =ExoPlayerFactory.newSimpleInstance(
            context,
            trackSelector,
            loadControl
        )
        val uri = RawResourceDataSource.buildRawResourceUri(R.raw.videoplayback)
        val mediaSource = buildMediaSourceNew(uri)
        player = simpleExoPlayer
        keepScreenOn = true
        simpleExoPlayer.playWhenReady = false
        simpleExoPlayer.prepare(mediaSource)
        simpleExoPlayer.addListener(object : Player.EventListener {
            override fun onTimelineChanged(timeline: Timeline?, manifest: Any?, reason: Int) {
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

            }

            override fun onPositionDiscontinuity(reason: Int) {

            }

            override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters?) {

            }

            override fun onSeekProcessed() {

            }

        })
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun pause() {
        PlayOperations.removeSelf(this)
        simpleExoPlayer.playWhenReady = false
        simpleExoPlayer.playbackState
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun play() {
        PlayOperations.pauseOther(this)
        simpleExoPlayer.playWhenReady = true
        simpleExoPlayer.playbackState
    }
}