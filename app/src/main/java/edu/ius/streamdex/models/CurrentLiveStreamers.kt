package edu.ius.streamdex.models

import java.lang.Exception
import java.lang.RuntimeException

class CurrentLiveStreamers {
    private val _liveStreamers: MutableList<Streamer> = mutableListOf()
    val liveStreamers: MutableList<Streamer>
        get() {
            return _liveStreamers
        }

    fun addStreamer(streamer: Streamer) {
        if (!streamer.currentlyLive) {
            throw RuntimeException("You cannot add a live streamer to CurrenLiveStreamer unless they are currently live!")
        }
        _liveStreamers.add(streamer)
    }

    fun addStreamer(streamers: List<Streamer>) {
        streamers.forEach { _liveStreamers.add(it) }
    }

    fun removeStreamerByName(name: String) {
        val streamerToRemove = _liveStreamers.find { it.name === name }
        _liveStreamers.remove(streamerToRemove)
    }

    fun clearAllStreamers() {
        _liveStreamers.clear()
    }
}