package edu.ius.streamdex.controllers

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import edu.ius.streamdex.R
import edu.ius.streamdex.ui.home.StreamRecyclerViewAdapter
import edu.ius.streamdex.api.TwitchRepository
import edu.ius.streamdex.api.TwitchStream
import edu.ius.streamdex.models.CurrentLiveStreamers
import edu.ius.streamdex.models.FavoriteStreamers
import edu.ius.streamdex.models.Stream
import edu.ius.streamdex.models.Streamer
import edu.ius.streamdex.ui.home.StreamFragment

class StreamListController(val owner: LifecycleOwner) {

    private val streamerController = StreamerController(owner)
    var streamList = mutableListOf<Stream>()
    private var streamResponse = mutableListOf<TwitchStream>()
    var streamersStored = false
    var streamersLive = false

    fun populateLiveStreams(view: View, recyclerView: RecyclerView) {
        streamList = mutableListOf()
        streamResponse = mutableListOf()
        streamerController.streamerList.observe(owner) { streamers ->
            Log.d("STORAGE_CALLBACK", "hit the storage observer")
            if (streamers != null && streamers.size > 0) {
                streamersStored = true
                val liveStreamers: List<Streamer> = streamers.filter {
                    it.currentlyLive
                }
                val idList = mutableListOf<Int>()
                liveStreamers.forEach { idList.add(it.twitchId) }

                if (idList.size > 0) {
                    streamersLive = true
                    TwitchRepository.getStreams(idList).observe(owner, { streams ->
                        streamResponse.addAll(streams.data)
                        transferResponseToList()
                    })
                }
            }
            updateUI(view, recyclerView)
        }
    }

    fun updateUI(view: View, recyclerView: RecyclerView) {
        val noFavoritesText = view.findViewById<TextView>(R.id.no_favorites)
        val noOneLiveText = view.findViewById<TextView>(R.id.no_live)

        if (!streamersStored) {
            noFavoritesText.visibility = View.VISIBLE
            noOneLiveText.visibility = View.INVISIBLE
        }
        else if (!streamersLive) {
            noFavoritesText.visibility = View.INVISIBLE
            noOneLiveText.visibility = View.VISIBLE
        }
        else {
            noFavoritesText.visibility = View.INVISIBLE
            noOneLiveText.visibility = View.INVISIBLE
        }
        recyclerView.adapter = StreamRecyclerViewAdapter(streamList)
    }

    private fun transferResponseToList() {
        streamResponse.forEach {
            val newStream = Stream(it.title, it.user_name, null, it.type == "live")
            streamList.add(newStream)
        }
    }

}