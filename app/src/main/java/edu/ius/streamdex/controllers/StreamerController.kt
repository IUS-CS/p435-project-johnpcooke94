package edu.ius.streamdex.controllers

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.ListUpdateCallback
import androidx.recyclerview.widget.RecyclerView
import edu.ius.streamdex.models.FavoriteStreamers
import edu.ius.streamdex.models.Streamer
import edu.ius.streamdex.storage.StreamerRepository
import edu.ius.streamdex.ui.streamers.FavoriteStreamerRecyclerViewAdapter
import kotlinx.coroutines.*
import java.util.function.UnaryOperator

class StreamerController (owner: LifecycleOwner) {

    val owner = owner
    var streamerList = MutableLiveData<MutableList<Streamer>>()

    init {
        populateLiveStreamers()
    }

    private fun populateLiveStreamers() {
        StreamerRepository.get().getStreamers().observe(owner, {streamers ->
            if (streamers != null) {
                val newList = mutableListOf<Streamer>()
                newList.addAll(streamers)
                streamerList.postValue(newList)
            }
        })
    }

    fun addStreamer(streamer: Streamer) {
        val job = Job()
        val scope = CoroutineScope(Dispatchers.Main + job)

        scope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                storeNewStreamer(streamer)
            }
        }
        val newList = mutableListOf<Streamer>()
        newList.addAll(streamerList.value!!)
        newList.add(streamer)
        streamerList.postValue(newList)

    }

    private suspend fun storeNewStreamers(streamers: List<Streamer>) {
        StreamerRepository.get().addStreamers(streamers)
    }

    private suspend fun storeNewStreamer(streamer: Streamer) {
        StreamerRepository.get().addStreamers(streamer)
    }

}