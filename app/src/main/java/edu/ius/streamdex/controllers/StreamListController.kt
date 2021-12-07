package edu.ius.streamdex.controllers

import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import edu.ius.streamdex.ui.home.StreamRecyclerViewAdapter
import edu.ius.streamdex.api.TwitchRepository
import edu.ius.streamdex.api.TwitchStream
import edu.ius.streamdex.models.FavoriteStreamers
import edu.ius.streamdex.models.Stream

class StreamListController(owner: LifecycleOwner) {

    val streamerController = StreamerController(owner)
    val owner = owner
    var streamList = mutableListOf<Stream>()
    private var streamResponse = mutableListOf<TwitchStream>()

    fun populateLiveStreams(owner: LifecycleOwner, view: RecyclerView) {
        streamList = mutableListOf()
        streamResponse = mutableListOf()
        TwitchRepository.getStreams().observe(owner, { streams ->
            streamResponse.addAll(streams.data)
            transferResponseToList()
            view.adapter = StreamRecyclerViewAdapter(streamList)
        })
    }

    private fun transferResponseToList() {
        streamResponse.forEach {
            val newStream = Stream(it.title, it.user_name, null, it.type == "live")
            streamList.add(newStream)
        }
    }

}