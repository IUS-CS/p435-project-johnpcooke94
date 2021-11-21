package edu.ius.streamdex

import edu.ius.streamdex.models.CurrentLiveStreamers
import edu.ius.streamdex.models.FavoriteStreamers
import edu.ius.streamdex.models.Streamer
import org.junit.Test
import org.junit.Assert.*
import java.lang.RuntimeException

class ModelTests {

    private val favoriteStreamers = FavoriteStreamers()
    private val liveStreamers = CurrentLiveStreamers()

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
            "xx_test_xx",
            "www.youtube.com/test",
            false,
            ""
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
                "xx_test_xx",
                "www.youtube.com/test",
                false,
                ""
            )
        )
        newStreamers.add(
            Streamer(
                "xx_test2_xx",
                "www.youtube.com/test2",
                true,
                "trying out Valorant XD"
            )
        )

        favoriteStreamers.addStreamer(newStreamers)
        assertEquals(favoriteStreamers.favoriteStreamers, newStreamers)
    }

    @Test
    fun testAddLiveStreamer() {
        val newStreamer = Streamer(
            "xx_test_xx",
            "www.youtube.com/test",
            true,
            "trying out Valorant XD"
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
                "xx_test_xx",
                "www.youtube.com/test",
                false,
                ""
            )
        )
        newStreamers.add(
            Streamer(
                "xx_test2_xx",
                "www.youtube.com/test2",
                true,
                "trying out Valorant XD"
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
                    "xx_test2_xx",
                    "www.youtube.com/test2",
                    true,
                    "trying out Valorant XD"
                )
            )
        }
        if (liveStreamers.liveStreamers == mutableListOf<Streamer>()) {
            liveStreamers.addStreamer(
                Streamer(
                    "xx_test2_xx",
                    "www.youtube.com/test2",
                    true,
                    "trying out Valorant XD"
                )
            )
        }

        favoriteStreamers.clearAllStreamers()
        assertEquals(favoriteStreamers.favoriteStreamers, mutableListOf<Streamer>())

        liveStreamers.clearAllStreamers()
        assertEquals(liveStreamers.liveStreamers, mutableListOf<Streamer>())
    }

}