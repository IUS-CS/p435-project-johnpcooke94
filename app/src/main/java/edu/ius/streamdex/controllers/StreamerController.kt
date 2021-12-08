package edu.ius.streamdex.controllers

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.observe
import edu.ius.streamdex.api.TwitchRepository
import edu.ius.streamdex.models.FavoriteStreamers
import edu.ius.streamdex.models.Stream
import edu.ius.streamdex.models.Streamer
import edu.ius.streamdex.storage.StreamerRepository
import kotlinx.coroutines.*

class StreamerController (val owner: LifecycleOwner) {

    private val model = FavoriteStreamers.get()
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
                var newList = mutableListOf<Streamer>()
                newList.addAll(streamers)
                streamerList.postValue(newList)

                val idList = mutableListOf<Int>()
                streamers.forEach { idList.add(it.twitchId) }
                TwitchRepository.getStreamers(idList).observe(owner) { response ->
                    if (response != null && response.data.isNotEmpty()) {
                        newList = mutableListOf()
                        response.data.forEach {
                            if (idList.contains(it.id)) {
                                newList.add(Streamer(it.display_name, it.id, "", it.is_live, it.title))
                            }
                        }
                        streamerList.postValue(newList)
                        updateStreamersInStorage(newList)
                    }
                }
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

    fun updateStreamersInStorage(streamers: List<Streamer>) {
        val job = Job()
        val scope = CoroutineScope(Dispatchers.Main + job)

        scope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                storeNewStreamer(streamers)
            }
        }

    }

    private suspend fun storeNewStreamer(streamer: Streamer) {
        StreamerRepository.get().addStreamers(streamer)
    }

    private suspend fun storeNewStreamer(streamer: List<Streamer>) {
        StreamerRepository.get().addStreamers(streamer)
    }

}