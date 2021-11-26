package edu.ius.streamdex

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView

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
        holder.idView.text = when {
            stream.live -> "Live!"
            else -> "Not live"
        }
        holder.contentView.text = stream.title
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentStreamBinding) : RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.itemNumber
        val contentView: TextView = binding.content

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

}