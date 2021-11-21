package edu.ius.streamdex.controllers

import edu.ius.streamdex.models.Stream

class StreamListController {

    val streamList = mutableListOf<Stream>()

    private val imPomu = Stream(
        "【MINECRAFT】let's have a grand old time【NIJISANJI EN | Pomu Rainpuff】",
        "https://youtu.be/ZzFsTGIcZ2g",
        false
        )

    init {
        streamList.add(imPomu)
    }

}