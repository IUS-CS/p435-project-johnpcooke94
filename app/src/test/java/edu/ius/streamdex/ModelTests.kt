package edu.ius.streamdex

import edu.ius.streamdex.models.CurrentLiveStreamers
import edu.ius.streamdex.models.FavoriteStreamers
import edu.ius.streamdex.models.Streamer
import org.junit.Test
import org.junit.Assert.*
import java.lang.RuntimeException

class ModelTests {

    private val favoriteStreamers = FavoriteStreamers.get()
    private val liveStreamers = CurrentLiveStreamers.get()

    // All lists should be empty at this point
    @Test
    fun testEmptyLists() {
        assertTrue(favoriteStreamers.favoriteStreamers == mutableListOf<Streamer>())
        assertTrue(liveStreamers.liveStreamers == mutableListOf<Streamer>())
    }

    @Test
    fun testAddFavoriteStreamer() {
        favoriteStreamers.clearAllStreamers()
        val newStreamer = Streamer(
            "test",
            100,
            "twitch.tv",
            false,
            "test stream"
        )
        favoriteStreamers.addStreamer(newStreamer)
        assertTrue(favoriteStreamers.favoriteStreamers == mutableListOf(newStreamer))
    }

    @Test
    fun testAddListOfFavoriteStreamers() {
        favoriteStreamers.clearAllStreamers()
        val newStreamers = mutableListOf<Streamer>()
        newStreamers.add(
            Streamer(
                "test",
                100,
                "twitch.tv",
                false,
                "test stream"
            )
        )
        newStreamers.add(
            Streamer(
                "test2",
                101,
                "twitch.tv/streamer2",
                true,
                "test stream2"
            )
        )

        favoriteStreamers.addStreamer(newStreamers)
        assertEquals(favoriteStreamers.favoriteStreamers, newStreamers)
    }

    @Test
    fun testAddLiveStreamer() {
        liveStreamers.clearAllStreamers()
        val newStreamer = Streamer(
            "test",
            100,
            "twitch.tv",
            true,
            "test stream"
        )
        liveStreamers.addStreamer(newStreamer)
        assertTrue(liveStreamers.liveStreamers == mutableListOf(newStreamer))

        newStreamer.currentlyLive = false
        assertThrows(RuntimeException::class.java) {
            liveStreamers.addStreamer(newStreamer)
        }
    }

    @Test
    fun testAddListOfLiveStreamers() {
        liveStreamers.clearAllStreamers()
        val newStreamers = mutableListOf<Streamer>()
        newStreamers.add(
            Streamer(
                "test",
                100,
                "twitch.tv",
                false,
                "test stream"
            )
        )
        newStreamers.add(
            Streamer(
                "test2",
                101,
                "twitch.tv/streamer2",
                true,
                "test stream2"
            )
        )

        liveStreamers.addStreamer(newStreamers)
        assertEquals(liveStreamers.liveStreamers, newStreamers)
    }

    @Test
    fun testClearStreamers() {
        if (favoriteStreamers.favoriteStreamers == mutableListOf<Streamer>()) {
            favoriteStreamers.addStreamer(
                Streamer(
                    "test2",
                    101,
                    "twitch.tv/streamer2",
                    true,
                    "test stream2"
                )
            )
        }
        if (liveStreamers.liveStreamers == mutableListOf<Streamer>()) {
            liveStreamers.addStreamer(
                Streamer(
                    "test2",
                    101,
                    "twitch.tv/streamer2",
                    true,
                    "test stream2"
                )
            )
        }

        favoriteStreamers.clearAllStreamers()
        assertEquals(favoriteStreamers.favoriteStreamers, mutableListOf<Streamer>())

        liveStreamers.clearAllStreamers()
        assertEquals(liveStreamers.liveStreamers, mutableListOf<Streamer>())
    }

}