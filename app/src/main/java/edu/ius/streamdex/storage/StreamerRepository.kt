package edu.ius.streamdex.storage

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import edu.ius.streamdex.models.Streamer

private const val DATABASE_NAME  = "streamer-database"

class StreamerRepository private constructor(context: Context) {

    private val database: StreamerDatabase = Room.databaseBuilder(
        context.applicationContext,
        StreamerDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val streamerDao = database.streamerDao()

    fun getStreamers(): LiveData<List<Streamer>?> = streamerDao.getStreamers()

    fun getStreamer(name: String): LiveData<Streamer?> = streamerDao.getStreamer(name)

    companion object {
        private var INSTANCE: StreamerRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = StreamerRepository(context)
            }
        }

        fun get(): StreamerRepository {
            return INSTANCE ?:
            throw IllegalStateException("Streamer repository must be initialized first.")
        }

    }

}