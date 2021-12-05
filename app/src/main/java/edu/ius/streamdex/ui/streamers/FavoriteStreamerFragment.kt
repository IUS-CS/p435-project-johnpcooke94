package edu.ius.streamdex.ui.streamers

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import edu.ius.streamdex.R
import edu.ius.streamdex.controllers.StreamerController
import edu.ius.streamdex.models.Streamer

/**
 * A fragment representing a list of Streamers.
 */

private val TAG = "STREAMER_FRAGMENT"

class FavoriteStreamerFragment : Fragment() {

    private var columnCount = 1
    private val listController = StreamerController()
    private var adapter = FavoriteStreamerRecyclerViewAdapter(emptyList())
    private lateinit var streamerRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val owner = this
        var view: View = inflater.inflate(R.layout.fragment_streamer_list, container, false)

        streamerRecyclerView = view.findViewById(R.id.streamer_recycler)
        listController.populateLiveStreamers(owner)

        val addButton = view.findViewById<FloatingActionButton>(R.id.add_streamer_button)
        addButton.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction().add(R.id.nav_host_fragment_content_main, AddStreamerFragment())
            transaction.hide(this)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textView = view.findViewById<TextView>(R.id.no_data)
        listController.streamerList.observe(viewLifecycleOwner) {streamers ->
            if (streamers.isNotEmpty()) {
                updateUI(streamers)
                textView.text = ""
                Log.d(TAG, "Streamers found in DB")
                Log.d(TAG, streamers.toString())
            }
        }
    }

    fun updateUI(streamers: List<Streamer>) {
        adapter = FavoriteStreamerRecyclerViewAdapter(streamers)
        streamerRecyclerView.adapter = adapter
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            FavoriteStreamerFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}