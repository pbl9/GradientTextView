package com.pbl9.GradientTextView

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView

class GradientAdapter(val onOrientationChanged: (GradientDrawable.Orientation) -> Unit):
    RecyclerView.Adapter<GradientAdapter.ViewHolder>() {

    val items: List<GradientDrawable.Orientation> = GradientDrawable.Orientation.values().toList()

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val button = itemView.findViewById<Button>(R.id.button)
        fun bind(orientation: GradientDrawable.Orientation, onOrientationChanged: (GradientDrawable.Orientation) -> Unit) {
            button.text = orientation.name
            button.setOnClickListener {
                onOrientationChanged(orientation)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.item_button, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item, onOrientationChanged)
    }

    override fun getItemCount(): Int = items.size
}