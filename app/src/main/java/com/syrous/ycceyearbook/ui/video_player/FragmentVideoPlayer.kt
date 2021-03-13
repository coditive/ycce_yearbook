package com.syrous.ycceyearbook.ui.video_player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.coroutineScope
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.util.Util
import com.syrous.ycceyearbook.databinding.FragmentVideoPlayerBinding
import com.syrous.ycceyearbook.flux.Presenter
import com.syrous.ycceyearbook.model.Resource
import com.syrous.ycceyearbook.presenter.VideoPlayerPresenter
import com.syrous.ycceyearbook.presenter.VideoPlayerView
import com.syrous.ycceyearbook.ui.BaseFragment
import com.syrous.ycceyearbook.util.Constant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber


class FragmentVideoPlayer: BaseFragment(), VideoPlayerView {
    override var presenter: Presenter = VideoPlayerPresenter(this)
    private lateinit var binding: FragmentVideoPlayerBinding
    private var player: SimpleExoPlayer? = null
    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition: Long = 0
    private lateinit var resource: Resource
    private val _selectedResource = MutableSharedFlow<Resource>()
    override val selectedResource: SharedFlow<Resource>
        get() = _selectedResource

    override val coroutineScope: CoroutineScope
        get() = viewLifecycleOwner.lifecycle.coroutineScope

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVideoPlayerBinding.inflate(inflater, container, false)
        resource = arguments?.getSerializable(Constant.Resource) as Resource
        Timber.d("Resource object : $resource")

        if (player == null) {
            initializeVideoPlayer()
        }
        return binding.root
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT >= 24) {
            releasePlayer()
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        coroutineScope.launch {
            _selectedResource.emit(resource)
        }
    }

    private fun initializeVideoPlayer() {
        player = SimpleExoPlayer.Builder(requireContext()).build()
        binding.videoView.player = player
    }


    private fun releasePlayer() {
        if (player != null) {
            playWhenReady = player!!.playWhenReady
            playbackPosition = player!!.currentPosition
            currentWindow = player!!.currentWindowIndex
            player!!.release()
            player = null
        }
    }

    override fun addVideoToPlayer(mediaItem: MediaItem) {
        player?.setMediaItem(mediaItem)
        player!!.playWhenReady = playWhenReady
        player!!.seekTo(currentWindow, playbackPosition)
        player!!.prepare()
    }
}