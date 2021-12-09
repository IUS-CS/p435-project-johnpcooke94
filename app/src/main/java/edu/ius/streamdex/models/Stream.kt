package edu.ius.streamdex.models

import android.graphics.Bitmap

class Stream (
    val title: String,
    val streamer: String,
    val link: String?,
    val live: Boolean,
    val thumbnailUrl: String,
    var thumbnailBmp: Bitmap? = null
) {

}