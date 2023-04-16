package com.example.exoplayersample.data

import android.net.Uri
import androidx.media3.common.MediaItem

data class VideoItem(
    val contentUri: Uri,
    val mediaItem: MediaItem,
    val name: String,
    val placeHolder: String = "https://wallpaperaccess.com/full/899965.jpg",
)