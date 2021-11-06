package edu.ius.streamdex.models

class CurrentLiveStreamers {
    private val _liveStreamers: MutableList<Streamer> = mutableListOf()
    val liveStreamers: MutableList<Streamer>
        get() {
            return _liveStreamers
        }

    fun addStreamer(streamer: Streamer) {
        _liveStreamers.add(streamer)
    }

    fun addStreamers(streamers: List<Streamer>) {
        streamers.forEach { _liveStreamers.add(it) }
    }

    fun removeStreamerByName(name: String) {
        val streamerToRemove = _liveStreamers.find { it.name === name }
        _liveStreamers.remove(streamerToRemove)
    }
}