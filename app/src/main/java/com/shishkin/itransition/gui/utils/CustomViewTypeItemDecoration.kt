package com.shishkin.itransition.gui.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView

class CustomViewTypeItemDecoration(context: Context, orientation: Int, private val drawableView: Drawable) : DividerItemDecoration(context, orientation) {

    // TODO Evgeny Зачем тебе тут View Type, и в NbaPlayersListAdapter?
    // Если я тут захочу поменять  VIEW_TYPE_NBA_TEAM на 20, все пойдет по попе.
    companion object {
        const val VIEW_TYPE_NBA_PLAYER = 1
        const val VIEW_TYPE_NBA_TEAM = 2
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {


        val d = drawableView
        for (i in 0 until parent.childCount) {
            val view = parent.getChildAt(i)
            val position = parent.getChildAdapterPosition(view)
            val viewType = parent.adapter!!.getItemViewType(position)

            if (viewType == VIEW_TYPE_NBA_TEAM) {
                val params = view.layoutParams as RecyclerView.LayoutParams
                val top = view.bottom + params.bottomMargin
                val bottom = top + d.intrinsicHeight
                d.setBounds(0, top, parent.right, bottom)
                d.draw(c)
            }
        }
    }
}
