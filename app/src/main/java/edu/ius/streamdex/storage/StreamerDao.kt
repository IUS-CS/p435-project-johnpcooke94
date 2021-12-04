package edu.ius.streamdex.storage

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import edu.ius.streamdex.models.Streamer

@Dao
interface StreamerDao {

    @Query("SELECT * FROM streamer")
    fun getStreamers(): LiveData<List<Streamer>?>

    @Query("SELECT * FROM streamer WHERE name=(:name)")
    fun getStreamer(name: String): LiveData<Streamer?>

}