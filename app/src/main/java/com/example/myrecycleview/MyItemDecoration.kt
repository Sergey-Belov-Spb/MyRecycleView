package com.example.myrecycleview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

public class MyItemDecoration(WigthLineSeparator: Int) : RecyclerView.ItemDecoration() {
        private var mWigthLineSeparator: Int = WigthLineSeparator

        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {

            val itemPosition = (view.getLayoutParams() as RecyclerView.LayoutParams).viewAdapterPosition
            if (itemPosition < 1) {
                outRect.top = 0
            } else {
                outRect.top = mWigthLineSeparator
            }

            outRect.bottom = 0
        }
}