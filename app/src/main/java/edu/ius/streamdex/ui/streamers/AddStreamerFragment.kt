package edu.ius.streamdex.ui.streamers

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import edu.ius.streamdex.R
import edu.ius.streamdex.api.StreamerResponse
import edu.ius.streamdex.api.TwitchRepository
import edu.ius.streamdex.controllers.StreamerController
import edu.ius.streamdex.models.Streamer

private val TAG = "ADD_STREAMER"

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddStreamerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddStreamerFragment(streamerController: StreamerController) : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val controller = streamerController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

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
                            controller.addStreamer(Streamer(it.display_name, "", it.is_live, it.title))
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

/*    companion object {
        *//**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddStreamerFragment.
         *//*
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddStreamerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }*/
}