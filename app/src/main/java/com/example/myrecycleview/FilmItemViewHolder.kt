package com.example.myrecycleview

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.log

class FilmItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val imgsArray = arrayOf(R.drawable.film_0,
        R.drawable.film_1,
        R.drawable.film_2,
        R.drawable.film_3,
        R.drawable.film_4,
        R.drawable.film_5,
        R.drawable.film_6,
        R.drawable.film_7,
        R.drawable.film_8,
        R.drawable.film_9,
        R.drawable.film_10,
        R.drawable.film_11,
        R.drawable.film_12)
    val titleTv: TextView = itemView.findViewById(R.id.titleTv)
    val subtitleTv: TextView = itemView.findViewById(R.id.subtitleTv)
    val imgIv: ImageView = itemView.findViewById(R.id.image)

    fun bind(item: FilmItem) {
        titleTv.text = item.name
        subtitleTv.text = item.description
        imgIv.setBackgroundColor(item.color)
        imgIv.setImageResource(imgsArray[0])

        titleTv.setTextColor(item.color)
        if (item.NumPic<=12) imgIv.setImageResource(imgsArray[item.NumPic])

        Log.d("__ViewHolder", "Num [${item.NumPic}]")
    }
}