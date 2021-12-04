package edu.ius.streamdex.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Streamer(@PrimaryKey val name: String, var channelLink: String, var currentlyLive: Boolean, var currentStream: String) {
}