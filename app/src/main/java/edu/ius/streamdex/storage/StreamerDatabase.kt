package edu.ius.streamdex.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ius.streamdex.models.Streamer

@Database(entities = [Streamer::class], version = 5)
abstract class StreamerDatabase: RoomDatabase() {

    abstract fun streamerDao(): StreamerDao

}