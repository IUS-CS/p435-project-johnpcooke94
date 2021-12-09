package edu.ius.streamdex.controllers

import android.graphics.Bitmap
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener
import edu.ius.streamdex.R
import edu.ius.streamdex.ui.home.StreamRecyclerViewAdapter
import edu.ius.streamdex.api.TwitchRepository
import edu.ius.streamdex.api.TwitchStream
import edu.ius.streamdex.models.Stream
import edu.ius.streamdex.models.Streamer

class StreamListController(
    val owner: LifecycleOwner,
    val view: View,
    val recyclerView: RecyclerView
    ) {

    private val streamerController = StreamerController(owner)
    var streamList = mutableListOf<Stream>()
    private var streamResponse = mutableListOf<TwitchStream>()
    var streamersStored = false
    var streamersLive = false

    fun populateLiveStreams() {
        streamList = mutableListOf()
        streamResponse = mutableListOf()
        streamerController.streamerList.observe(owner) { streamers ->
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
            updateUI()
            //downloadThumbnails()
        }
    }

    fun updateUI() {
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
            val regex = Regex("\\{width\\}x\\{height\\}")
            val newString = it.thumbnail_url.replace(regex, "272x153")
            val newStream = Stream(
                it.title,
                it.user_name,
                null,
                it.type == "live",
                newString
            )
            streamList.add(newStream)
        }
    }

/*    fun downloadThumbnails() {
        val imageLoader = ImageLoader.getInstance()
        streamList.forEach { stream ->
            imageLoader.loadImage(stream.thumbnailUrl, object: SimpleImageLoadingListener() {
                override fun onLoadingComplete(
                    imageUri: String?,
                    view: View?,
                    loadedImage: Bitmap?
                ) {
                    if (loadedImage != null) {
                        stream.thumbnailBmp = loadedImage
                        updateUI()
                    }
                }
            })
        }
    }*/

}