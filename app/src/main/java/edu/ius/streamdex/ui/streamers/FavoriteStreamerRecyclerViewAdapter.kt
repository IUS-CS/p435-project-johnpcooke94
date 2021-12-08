package edu.ius.streamdex.ui.streamers

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import edu.ius.streamdex.databinding.FragmentStreamerBinding
import edu.ius.streamdex.models.Streamer

class FavoriteStreamerRecyclerViewAdapter(
    private val values: List<Streamer>
) : RecyclerView.Adapter<FavoriteStreamerRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentStreamerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        if (item.currentlyLive) holder.liveView.text = "LIVE!" else holder.liveView.text = "Offline"
        holder.lastLiveTitle.text = item.currentStream
        holder.nameView.text = item.name
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentStreamerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val liveView: TextView = binding.liveStatus
        val nameView: TextView = binding.streamerName
        val lastLiveTitle: TextView = binding.streamTitle

        override fun toString(): String {
            return super.toString() + " '" + nameView.text + "'"
        }
    }

}