package edu.ius.streamdex.storage

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import edu.ius.streamdex.models.Streamer

@Dao
interface StreamerDao {

    @Query("SELECT * FROM streamers")
    fun getStreamers(): LiveData<List<Streamer>?>

    @Query("SELECT * FROM streamers WHERE name=(:name)")
    fun getStreamer(name: String): LiveData<Streamer?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addStreamers(streamers: List<Streamer>)

}