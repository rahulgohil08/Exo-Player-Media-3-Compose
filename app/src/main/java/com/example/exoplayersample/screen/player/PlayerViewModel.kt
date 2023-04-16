package com.example.exoplayersample.screen.player

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.net.toUri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import com.example.exoplayersample.data.VideoItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    val player: Player,
) : ViewModel() {

    companion object {
        private const val TAG = "PlayerViewModel"
    }

    private val _videos = savedStateHandle.getStateFlow("videoUris", mutableListOf<VideoItem>())
    val videos = _videos.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val currentVideo: MutableState<VideoItem?> = mutableStateOf(null)
    val isPlayerLoading = mutableStateOf(false)
    val visibleTitle = mutableStateOf(true)


    init {
        player.apply {
            createFakeList()
            prepare()
        }
    }

    private fun createFakeList() {
        // Create a mutable list to store the fake video items
        val fakeVideos = mutableListOf<VideoItem>()

        repeat(3) {

            fakeVideos.add(
                VideoItem(
                    contentUri = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4".toUri(),
                    name = "Bunny",
                    mediaItem = MediaItem.fromUri("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4".toUri())
                )
            )

            fakeVideos.add(
                VideoItem(
                    contentUri = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4".toUri(),
                    name = "Some Random Stuff",
                    mediaItem = MediaItem.fromUri("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4".toUri())
                )
            )
        }
        savedStateHandle["videoUris"] = fakeVideos
        currentVideo.value = fakeVideos.first()
        player.addMediaItems(fakeVideos.map { it.mediaItem })
    }

    fun playVideo(uri: Uri) {
        player.setMediaItem(
            MediaItem.fromUri(uri)
        )
        currentVideo.value = videos.value.find { it.contentUri == uri } ?: return
    }

    override fun onCleared() {
        super.onCleared()
        player.apply {
            release()
        }
    }
}