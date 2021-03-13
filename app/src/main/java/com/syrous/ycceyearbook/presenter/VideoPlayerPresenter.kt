package com.syrous.ycceyearbook.presenter

import android.content.Context
import com.google.android.exoplayer2.MediaItem
import com.syrous.ycceyearbook.YearBookApplication
import com.syrous.ycceyearbook.flux.Presenter
import com.syrous.ycceyearbook.model.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


interface VideoPlayerView {
    val selectedResource: SharedFlow<Resource>
    val coroutineScope: CoroutineScope
    fun addVideoToPlayer(mediaItem: MediaItem)
}

class VideoPlayerPresenter (
    private val view: VideoPlayerView
    ): Presenter() {

    override fun onViewReady() {
        super.onViewReady()
        view.coroutineScope.launch {
            launch {
                view.selectedResource.collect {
                    resource ->
                    createMediaItemForUrl(resource.url)
                }
            }

        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as YearBookApplication).appComponent.inject(this)
    }

    private fun createMediaItemForUrl(url: String) {
        val mediaItem: MediaItem = MediaItem.fromUri(url)
        view.addVideoToPlayer(mediaItem)
    }
}