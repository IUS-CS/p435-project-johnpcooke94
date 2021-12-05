package edu.ius.streamdex

import android.app.Application
import edu.ius.streamdex.models.Streamer
import edu.ius.streamdex.storage.StreamerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StreamDexApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        StreamerRepository.initialize(this)
    }

}