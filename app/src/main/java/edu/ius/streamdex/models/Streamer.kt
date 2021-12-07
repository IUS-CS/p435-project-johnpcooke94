package edu.ius.streamdex.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "streamers")
data class Streamer(
    @PrimaryKey var name: String,
    var twitchId: Int,
    var channelLink: String,
    var currentlyLive: Boolean,
    var currentStream: String
    )