package edu.ius.streamdex.controllers

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.observe
import edu.ius.streamdex.models.FavoriteStreamers
import edu.ius.streamdex.models.Streamer
import edu.ius.streamdex.storage.StreamerRepository
import kotlinx.coroutines.*

class StreamerController (owner: LifecycleOwner) {

    val owner = owner
    val model = FavoriteStreamers.get()
    var streamerList = MutableLiveData<MutableList<Streamer>>()

    init {
        streamerList.observe(owner) { streamers ->
            if (streamers != null) {
                model.clearAllStreamers()
                model.addStreamer(streamers)
            }
        }
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

    private suspend fun storeNewStreamer(streamer: Streamer) {
        StreamerRepository.get().addStreamers(streamer)
    }

}