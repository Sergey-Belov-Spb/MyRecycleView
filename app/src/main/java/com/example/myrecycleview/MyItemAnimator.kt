package com.example.myrecycleview

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.graphics.Interpolator
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_films.view.*
import java.util.*

class MyItemAnimator: DefaultItemAnimator() {

    override fun canReuseUpdatedViewHolder(viewHolder: RecyclerView.ViewHolder): Boolean {
        return true
    }

    override fun recordPreLayoutInformation(
        state: RecyclerView.State,
        viewHolder: RecyclerView.ViewHolder,
        changeFlags: Int,
        payloads: MutableList<Any>
    ): ItemHolderInfo {
        /*if (changeFlags == RecyclerView.ItemAnimator.FLAG_CHANGED){
            for (payload  in payloads) {
                if(payload  is String)
                    return CharacterItemHolderInfo(payload)
            }
        }*/
        return super.recordPreLayoutInformation(state, viewHolder, changeFlags, payloads)
    }

    override fun animateChange(
        oldHolder: RecyclerView.ViewHolder,
        newHolder: RecyclerView.ViewHolder,
        preInfo: ItemHolderInfo,
        postInfo: ItemHolderInfo
    ): Boolean {
        //if (preInfo is CharacterItemHolderInfo){
            //val recipesItemHolderInfo = preInfo as CharacterItemHolderInfo
            var holder = newHolder as RecyclerView.ViewHolder
            animateFilmLike(newHolder)//holder)
            /*if (CharacterRVAdapter.ACTION_LIKE_IMAGE_DOUBLE_CLICKED.equals(recipesItemHolderInfo.updateAction)) {
                animatePhotoLike(holder);
            }*/
        //}
        //return false
        return super.animateChange(oldHolder, newHolder, preInfo, postInfo);
    }
    private fun animateFilmLike( holder: RecyclerView.ViewHolder){

        val animatorSet = AnimatorSet()
        val scaleLikeIcon = ObjectAnimator.ofPropertyValuesHolder(holder.itemView.image, PropertyValuesHolder.ofFloat("ScaleX", 1.0f, 0.65f, 1.0f),
            PropertyValuesHolder.ofFloat("scaleY",  1.0f, 0.65f, 1.0f),PropertyValuesHolder.ofFloat("alpha", 1.0f, 0.05f, 1.0f)).apply {
            duration = 500
            interpolator  = LinearInterpolator()
        }

        AnimatorSet().apply {
            play(scaleLikeIcon)
            start()
        }
    }
}

