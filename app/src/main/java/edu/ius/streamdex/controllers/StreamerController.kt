package edu.ius.streamdex.controllers

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.ListUpdateCallback
import androidx.recyclerview.widget.RecyclerView
import edu.ius.streamdex.models.Streamer
import edu.ius.streamdex.storage.StreamerRepository
import edu.ius.streamdex.ui.streamers.FavoriteStreamerRecyclerViewAdapter

class StreamerController {

    var streamerList = MutableLiveData<List<Streamer>>()

    fun populateLiveStreamers(owner: LifecycleOwner) {
        StreamerRepository.get().getStreamers().observe(owner, {streamers ->
            if (streamers != null) {
                streamerList.value = streamers
            }
        })
    }

    suspend fun addFavoriteStreamers(streamers: List<Streamer>) {
        StreamerRepository.get().addStreamers(streamers)
    }

    suspend fun addFavoriteStreamers(streamer: Streamer) {
        StreamerRepository.get().addStreamers(streamer)
    }

}