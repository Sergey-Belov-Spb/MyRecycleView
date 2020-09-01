package com.example.myrecycleview

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import javax.security.auth.callback.Callback

class FilmAdapter(
    val inflater: LayoutInflater,
    val items: List<FilmItem>,
    val callback: Callback,
    val listener : (filmItem: FilmItem, position : Int) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val TAG = "FilmAdapter"
        const val VIEW_TYPE_HEADER = 0
        const val VIEW_TYPE_ITEM = 1
    }

    var TypeView = VIEW_TYPE_HEADER

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d(TAG, "onCreateViewHolder $viewType")
        /*if (viewType == VIEW_TYPE_ITEM) {
            return FilmItemViewHolder(inflater.inflate(R.layout.item_films, parent, false))
        } else*{
            return HeaderItemViewHolder(inflater.inflate(R.layout.item_film_header, parent, false))
        }*/
        TypeView = viewType

        return FilmItemViewHolder(inflater.inflate(R.layout.item_films, parent, false))
    }

    override fun getItemCount() = items.size  // +1 = header

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder $position")
        if (holder is FilmItemViewHolder) {
            val item = items[position ]
            holder.bind(item)
            holder.itemView.setOnClickListener {listener(item, position)}
        }

        holder.itemView.setOnLongClickListener { view ->
            Log.d(TAG, "setOnLongClickListener [$position]")
            if (position >= 0) {
                Log.d(TAG, "Add to favorite [$position] ")
                callback.onItemClicked(position )
            }
            true
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) VIEW_TYPE_HEADER else VIEW_TYPE_ITEM
    }

    interface Callback {
        fun onItemClicked(itemAdd: Int)
    }

}