package edu.ius.streamdex.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import edu.ius.streamdex.R
import edu.ius.streamdex.controllers.StreamListController
import edu.ius.streamdex.models.Stream

/**
 * A fragment representing a list of Streams.
 */
class StreamFragment : Fragment() {

    private var columnCount = 1
    private lateinit var listController: StreamListController
    private lateinit var streamView: View
    private lateinit var streamRecyclerView: RecyclerView

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
        streamView = inflater.inflate(R.layout.fragment_stream_list, container, false)
        streamRecyclerView = streamView.findViewById(R.id.list)
        listController = StreamListController(this, streamView, streamRecyclerView)

        // Set the adapter
        with(streamRecyclerView) {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            listController.populateLiveStreams()
        }
        return streamView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listController.updateUI()
    }

    companion object {

        const val ARG_COLUMN_COUNT = "column-count"

        @JvmStatic
        fun newInstance(columnCount: Int) =
            StreamFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}