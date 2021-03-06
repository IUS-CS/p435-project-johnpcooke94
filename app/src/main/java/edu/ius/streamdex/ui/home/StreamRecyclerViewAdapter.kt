package edu.ius.streamdex.ui.home

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.nostra13.universalimageloader.core.ImageLoader

import edu.ius.streamdex.databinding.FragmentStreamBinding
import edu.ius.streamdex.models.Stream


class StreamRecyclerViewAdapter(
    private val values: List<Stream>
) : RecyclerView.Adapter<StreamRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentStreamBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val stream = values[position]
        holder.liveStatusView.text = when {
            stream.live -> "Live!"
            else -> "Not live"
        }
        holder.streamTitleView.text = stream.title
        holder.streamerNameView.text = stream.streamer
        if (!stream.thumbnailUrl.contentEquals("")) {
            ImageLoader.getInstance().displayImage(stream.thumbnailUrl, holder.streamImageView)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentStreamBinding) : RecyclerView.ViewHolder(binding.root) {
        val liveStatusView: TextView = binding.liveStatus
        val streamTitleView: TextView = binding.streamTitle
        val streamerNameView: TextView = binding.streamerName
        val streamImageView: ImageView = binding.streamThumbnail

        override fun toString(): String {
            return super.toString() + " '" + streamTitleView.text + "'"
        }
    }

}