package com.abhijith.myapplication.ui.view

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.extractor.ExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory


class MySimpleExoPlayer : PlayerView, LifecycleObserver {

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr)

    constructor(context: Context?, attrs: AttributeSet?)
            : super(context, attrs)

    constructor(context: Context?, color: Int) : super(context) {

    }

    private val loadControl = DefaultLoadControl()
    private val bandwidthMeter = DefaultBandwidthMeter()
    private val trackSelector = DefaultTrackSelector(AdaptiveTrackSelection.Factory(bandwidthMeter))
    private var simpleExoPlayer: SimpleExoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector, loadControl)
    private val factory = DefaultHttpDataSourceFactory("exoplayer_video")
    private val extectorFactory = DefaultExtractorsFactory() as ExtractorsFactory

    fun setUri(uri: Uri) {
        val mediaSource = ExtractorMediaSource(uri, factory, extectorFactory, null, null)
        player = simpleExoPlayer
        keepScreenOn = true
        simpleExoPlayer.playWhenReady = true
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
        simpleExoPlayer.playWhenReady = false
        simpleExoPlayer.playbackState
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun start() {
        simpleExoPlayer.playWhenReady = true
        simpleExoPlayer.playbackState
    }
}