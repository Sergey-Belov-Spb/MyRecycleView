package com.example.myrecycleview

import android.app.Activity
import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myrecycleview.FilmAdapter.Callback
import kotlinx.android.synthetic.main.activity_about_film.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

data class DeletedFilm (val index: Int) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(index)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DeletedFilm> {
        override fun createFromParcel(parcel: Parcel): DeletedFilm {
            return DeletedFilm(parcel)
        }

        override fun newArray(size: Int): Array<DeletedFilm?> {
            return arrayOfNulls(size)
        }
    }
}

class AboutFilmActivity() : AppCompatActivity() {
    companion object{
        const val TAG = "AboutFilmActivity"

    }
    var itemsFilmsFavorite : MutableList<FilmItem> = ArrayList()
    var delItemmsFilm : ArrayList<DeletedFilm>  = ArrayList()
    var LastLongPress: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_film)

        val intent = getIntent()
        itemsFilmsFavorite = intent?.getParcelableArrayListExtra<FilmItem>("workoutlist")!!

        initRecycler()
        initClickListener()
    }

    private fun initClickListener(){
        findViewById<View>(R.id.closeBtn).setOnClickListener {
            val returnIntent = this.intent

            returnIntent.putParcelableArrayListExtra("deletedFilm",delItemmsFilm)
            setResult(Activity.RESULT_OK,returnIntent)
            this.finish()
        }
    }

    private fun initRecycler(){
        val recycler = findViewById<RecyclerView>(R.id.recyclerViewFavorite)
        val layoutManadger = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        recycler.layoutManager = layoutManadger

        recycler.adapter = FilmAdapter(
            LayoutInflater.from(this),
            itemsFilmsFavorite,
            object : Callback {
                override fun onItemClicked(item: Int) {
                    //Delete from favorite?
                    Log.d(TAG, "+++++onItemClicked Favorite = $item")
                    LastLongPress=item

                    val builder = AlertDialog.Builder(this@AboutFilmActivity)// Initialize a new instance of
                    builder.setTitle("Удаление фильма")// Set the alert dialog title
                    builder.setMessage("Вы уверены что хотите удалить этот фильм из избранного?")// Display a message on alert dialog
                    builder.setPositiveButton("Да"){dialog, which ->// Set a positive button and its click listener on alert dialog
                        // Do something when user press the positive button
                        //Toast.makeText(applicationContext,"Ok, we change the app background.",Toast.LENGTH_SHORT).show()
                        if (LastLongPress >= 0) {
                            if (LastLongPress > itemsFilmsFavorite.size) {LastLongPress = itemsFilmsFavorite.size-1}
                            itemsFilmsFavorite.removeAt(LastLongPress)
                            delItemmsFilm.add(DeletedFilm(LastLongPress));
                            recyclerViewFavorite.adapter?.notifyItemRemoved(LastLongPress)
                            recyclerViewFavorite.adapter?.notifyItemRangeChanged(LastLongPress, itemsFilmsFavorite.size);
                            LastLongPress=-1
                        }
                    }
                    builder.setNegativeButton("Нет"){ dialog, which ->// Display a negative button on alert dialog
                        //Toast.makeText(applicationContext,"You are not agree.",Toast.LENGTH_SHORT).show()
                    }
                    val dialog: AlertDialog = builder.create()// Finally, make the alert dialog using builder
                    dialog.show()// Display the alert dialog on app interface
                }
            }
        ){ filmItem, position ->
        }
        recycler.itemAnimator = MyItemAnimator()
        recycler.addItemDecoration(MyItemDecoration(20))
    }
}

