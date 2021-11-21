package edu.ius.streamdex.controllers

import edu.ius.streamdex.models.CurrentLiveStreamers
import edu.ius.streamdex.models.Stream
import edu.ius.streamdex.models.Streamer

class StreamListController {

    val streamList = mutableListOf<Stream>()

    val imPomu = Stream(
        "【MINECRAFT】let's have a grand old time【NIJISANJI EN | Pomu Rainpuff】",
        "https://youtu.be/ZzFsTGIcZ2g",
        false
        )

    init {
        streamList.add(imPomu)
    }

}