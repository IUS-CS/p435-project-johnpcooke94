package edu.ius.streamdex.models

class FavoriteStreamers {
    private val _favoriteStreamers: MutableList<Streamer> = mutableListOf()
    val favoriteStreamers: MutableList<Streamer>
        get() {
            return _favoriteStreamers
        }

    fun addStreamer(streamer: Streamer) {
        _favoriteStreamers.add(streamer)
    }

    fun addStreamers(streamers: List<Streamer>) {
        streamers.forEach { _favoriteStreamers.add(it) }
    }

    fun removeStreamerByName(name: String) {
        val streamerToRemove = _favoriteStreamers.find { it.name === name }
        _favoriteStreamers.remove(streamerToRemove)
    }

    fun clearAllStreamers() {
        _favoriteStreamers.clear()
    }

}