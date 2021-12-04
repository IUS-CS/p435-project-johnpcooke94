package edu.ius.streamdex.controllers

import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import edu.ius.streamdex.models.Streamer
import edu.ius.streamdex.storage.StreamerRepository
import edu.ius.streamdex.ui.streamers.FavoriteStreamerRecyclerViewAdapter

class StreamerController {

    var streamerList = mutableListOf<Streamer>()

    fun populateLiveStreamers(owner: LifecycleOwner, view: RecyclerView) {
        streamerList = mutableListOf()
        StreamerRepository.get().getStreamers().observe(owner, {streamers ->
            if (streamers != null) {
                streamerList.addAll(streamers)
            }
            view.adapter = FavoriteStreamerRecyclerViewAdapter(streamerList)
        })
    }

}