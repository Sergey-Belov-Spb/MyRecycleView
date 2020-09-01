package com.example.myrecycleview

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parceler
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FilmItem(val NumPic: Int, val name: String?, val description: String?, var color: Int)
    : Parcelable
{
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    companion object : Parceler<FilmItem> {

        override fun FilmItem.write(parcel: Parcel, flags: Int) {
            parcel.writeInt(NumPic)
            parcel.writeString(name)
            parcel.writeString(description)
            parcel.writeInt(color)
        }

        override fun create(parcel: Parcel): FilmItem {
            return FilmItem(parcel)
        }
    }
}
