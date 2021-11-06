package edu.ius.streamdex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import edu.ius.streamdex.models.FavoriteStreamers
import edu.ius.streamdex.models.Streamer

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val favoriteStreamers = FavoriteStreamers()
        favoriteStreamers.addStreamer(
            Streamer(
            "xx_test_xx",
            "www.youtube.com/test",
            false,
            "trying out Valorant XD"
            )
        )

        val streamerToDisplay = favoriteStreamers.favoriteStreamers[0]

        val mainTextView = findViewById<TextView>(R.id.mainTextView)
        mainTextView.text = "${streamerToDisplay.name}\n" +
                            "${streamerToDisplay.currentStream}\n" +
                            "${streamerToDisplay.currentlyLive}"

    }
}