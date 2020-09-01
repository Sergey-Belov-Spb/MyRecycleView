package com.example.myrecycleview

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationSet
import android.view.animation.DecelerateInterpolator
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myrecycleview.FilmAdapter.Callback
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_films.view.*

class MainActivity : AppCompatActivity() {
    companion object {
        const val TAG = "MainActivity"
    }
    var LastSelectedFilm :Int = -1

    var itemsFilms = mutableListOf(
        FilmItem(1,"Фильм 1", "подзаголовок 1 ", Color.GRAY),
        FilmItem(2,"Фильм 2", "подзаголовок 2 ", Color.GRAY),
        FilmItem(3,"Фильм 3", "подзаголовок 3 ", Color.GRAY),
        FilmItem(4,"Фильм 4", "подзаголовок 1 ", Color.GRAY),
        FilmItem(5,"Фильм 5", "подзаголовок 2 ", Color.GRAY),
        FilmItem(6,"Фильм 6", "подзаголовок 3 ", Color.GRAY),
        FilmItem(7,"Фильм 7", "подзаголовок 1 ", Color.GRAY),
        FilmItem(8,"Фильм 8", "подзаголовок 2 ", Color.GRAY),
        FilmItem(9,"Фильм 9", "подзаголовок 3 ", Color.GRAY),
        FilmItem(10,"Фильм 10", "подзаголовок 1 ", Color.GRAY),
        FilmItem(11,"Фильм 11", "подзаголовок 2 ", Color.GRAY),
        FilmItem(12,"Фильм 12", "подзаголовок 3 ", Color.GRAY)
    )
    var LastClickFilm : FilmItem = itemsFilms[0]

    val itemsFilmsFavorite : MutableList<FilmItem> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecycler()
        initClickListener()
        itemsFilmsFavorite.add(itemsFilms[0])
        itemsFilmsFavorite.add(itemsFilms[1])
        itemsFilmsFavorite.add(itemsFilms[2])
        itemsFilmsFavorite.add(itemsFilms[3])
    }

    private fun initClickListener(){
        findViewById<View>(R.id.addBtn).setOnClickListener {
            if (LastSelectedFilm>0) {
                itemsFilms.add(LastSelectedFilm,FilmItem(0, "NewFilm", "new film subtitle", Color.MAGENTA))

                recyclerView.adapter?.notifyItemInserted(LastSelectedFilm)
                recyclerView.adapter?.notifyItemRangeChanged(LastSelectedFilm, itemsFilms.size);
                //recyclerView.adapter?.notifyDataSetChanged()
            }
        }

        findViewById<View>(R.id.removeBtn).setOnClickListener {
            var i : Int = 0
            var CntrDeletedFile: Int =0
            //Ищем все отмеченные фильмы и удаляем их
            val itemsDeleted : ArrayList<Int>  = ArrayList()
            while (i < (itemsFilms.size) ) {
                Log.d(TAG, "i= $i  itemsFilms[i].color=$itemsFilms[i].color")
                if (itemsFilms[i].color == Color.RED) {
                    itemsFilms.removeAt(i)
                    CntrDeletedFile++
                    itemsDeleted.add(i)
                }
                else { i++}
            }
            //Если удалили несколько фильмов, то не понятно как сделать анимацию, поэтому просто обновляем recyclerView
            /*if (CntrDeletedFile>1){ recyclerView.adapter?.notifyDataSetChanged()}
            else
            {*/
            if (itemsDeleted.size>=1) {
                for (i in 0..itemsDeleted.size-1) {
                    recyclerView.adapter?.notifyItemRemoved(itemsDeleted[i]);
                }
                recyclerView.adapter?.notifyItemRangeChanged(itemsDeleted[0], itemsFilms.size);
                //recyclerView.adapter?.notifyDataSetChanged()
            }
            //}

        }

        findViewById<View>(R.id.switchToFavoriteBtn).setOnClickListener {
            Log.d(TAG, "switchToFavoriteBtn ")
            //Set all colors in itemsFilmsFavorite to Color.GRAY
            for (i in 0..itemsFilmsFavorite.size-1) {itemsFilmsFavorite[i].color=Color.GRAY}
            val intent = Intent(this,AboutFilmActivity::class.java)
            val listRandom= ArrayList<FilmItem>(itemsFilmsFavorite)
            intent.putParcelableArrayListExtra("workoutlist",listRandom)
            intent.putParcelableArrayListExtra("FavoriteFilms",itemsFilmsFavorite)
            //startActivity (intent)
            startActivityForResult(intent,0)
        }
    }

    private fun initRecycler (){
        val recycler = findViewById<RecyclerView>(R.id.recyclerView)
        //val layoutManager = GridLayoutManager(this,2)
        val layoutManager = LinearLayoutManager (this, LinearLayoutManager.VERTICAL,false)
        recycler.layoutManager = layoutManager
        recycler.adapter = FilmAdapter(
            LayoutInflater.from(this),
            itemsFilms,
            callback = object : Callback {
                override fun onItemClicked( item: Int) {
                    Log.d(TAG, "+++++onItemClicked = $item")
                    if (itemsFilmsFavorite.indexOf(itemsFilms[item]) == -1) {
                        itemsFilmsFavorite.add(itemsFilms[item])
                        Toast.makeText(
                            applicationContext,
                            "Фильм добавлен в избранное.",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.d(TAG, "itemsFilmsFavorite.size = ${itemsFilmsFavorite.size}")
                    }
                }
            }
        ){ filmItem, position ->
            Log.d(TAG, "----Short Click position= $position LastSelectedFilm = $LastSelectedFilm")
/*            if ((LastSelectedFilm >= 0)&&(LastSelectedFilm < itemsFilms.size))
            {
                itemsFilms[LastSelectedFilm].color = Color.GRAY
            }*/
            if (filmItem.color == Color.RED) filmItem.color = Color.GRAY
                else    filmItem.color = Color.RED
            recycler.adapter?.notifyItemChanged(position)
            LastSelectedFilm = position;
        }

        /*recycler.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (layoutManager.findLastVisibleItemPosition()== itemsFilms.size){
                    /*!!!No ADD data
                    repeat(4){
                        items.add(FilmItem(0,"New Film","-----",Color.BLACK))
                    }*/
                    recycler.adapter?.notifyItemRangeChanged(itemsFilms.size-4,4)
                }

            }
        })*/

        /*val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(getDrawable(R.drawable.black_line_5dp)!!)
        recycler.addItemDecoration(itemDecoration)*/

        recycler.addItemDecoration(MyItemDecoration(20))
        recycler.itemAnimator = MyItemAnimator()

        /*class CustomItenDecoration(context: Context, orientation: Int) :DividerItemDecoration(context,orientation) {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                super.getItemOffsets(outRect, view, parent, state)
            }

        }*/
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val DeletedFilmFavirite = (if (data != null) data.getParcelableArrayListExtra<DeletedFilm>("deletedFilm") else null)!!
            Log.d(TAG, "+++++DeletedFilmFavirite = ${DeletedFilmFavirite.size}")
            var i:Int=0
            repeat(DeletedFilmFavirite.size)
            {
                Log.d(TAG, "+++++DeletedFilmFavirite[0] = ${DeletedFilmFavirite[i].index}")
                itemsFilmsFavorite.removeAt(DeletedFilmFavirite[i].index)
                i++
            }
        }
    }
}
private fun Intent.putParcelableArrayListExtra(s: String, itemsFilmsFavorite: MutableList<FilmItem>) {
}
