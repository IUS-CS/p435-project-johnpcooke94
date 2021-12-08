package edu.ius.streamdex

import android.app.Application
import edu.ius.streamdex.models.FavoriteStreamers
import edu.ius.streamdex.storage.StreamerRepository

class StreamDexApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        StreamerRepository.initialize(this)
        FavoriteStreamers.initialize()
    }

}