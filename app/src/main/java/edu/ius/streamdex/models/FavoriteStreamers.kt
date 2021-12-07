package edu.ius.streamdex.models

class FavoriteStreamers private constructor(){
    private val _favoriteStreamers: MutableList<Streamer> = mutableListOf()
    val favoriteStreamers: MutableList<Streamer>
        get() {
            return _favoriteStreamers
        }

    fun addStreamer(streamer: Streamer) {
        _favoriteStreamers.add(streamer)
    }

    fun addStreamer(streamers: List<Streamer>) {
        streamers.forEach { _favoriteStreamers.add(it) }
    }

    fun removeStreamerByName(name: String) {
        val streamerToRemove = _favoriteStreamers.find { it.name === name }
        _favoriteStreamers.remove(streamerToRemove)
    }

    fun clearAllStreamers() {
        _favoriteStreamers.clear()
    }

    companion object {
        private var INSTANCE: FavoriteStreamers? = null

        fun initialize() {
            if (INSTANCE == null) {
                INSTANCE = FavoriteStreamers()
            }
        }

        fun get(): FavoriteStreamers {
            if (INSTANCE == null) {
                initialize()
                return INSTANCE!!
            }
            else {
                return INSTANCE!!
            }
        }

    }

}