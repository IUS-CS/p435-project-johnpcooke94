package edu.ius.streamdex.ui.streamers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import edu.ius.streamdex.R
import edu.ius.streamdex.api.TwitchRepository
import edu.ius.streamdex.controllers.StreamerController
import edu.ius.streamdex.models.Streamer

private val TAG = "ADD_STREAMER"

class AddStreamerFragment(streamerController: StreamerController) : Fragment() {
    val controller = streamerController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_add_streamer, container, false)
        val button = view.findViewById<Button>(R.id.add_button)

        button.setOnClickListener {
            val usernameInput = view.findViewById<EditText>(R.id.editText).text
            TwitchRepository.getStreamers(usernameInput.toString()).observe(viewLifecycleOwner, {twitchUsers ->
                if (twitchUsers != null && twitchUsers.data.isNotEmpty()) {
                    var found = false
                    twitchUsers.data.forEach {
                        if (it.display_name.contentEquals(usernameInput, true)) {
                            controller.addStreamer(Streamer(it.display_name, it.id, "", it.is_live, it.title))
                            found = true
                            parentFragmentManager.popBackStack()
                        }
                    }
                    if (!found) {
                        displayStreamerNotFound()
                    }
                }
                else {
                    displayStreamerNotFound()
                }
            })
        }

        return view
    }

    private fun displayStreamerNotFound() {
        Toast.makeText(
            this.context,
            "Could not find that Twitch streamer, check the name and try again",
            Toast.LENGTH_LONG)
            .show()
    }

}