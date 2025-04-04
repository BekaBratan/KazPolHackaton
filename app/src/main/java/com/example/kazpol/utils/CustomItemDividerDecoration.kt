package com.example.unihub.utils

import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.recyclerview.widget.RecyclerView

class CustomDividerItemDecoration(private val divider: Drawable): RecyclerView.ItemDecoration() {
    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {

        val dividerLeft = parent.paddingLeft
        val dividerRight = parent.width - parent.paddingRight

        Log.d("Divider", "Parent: $parent")


        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams

            val dividerTop = child.bottom + params.bottomMargin
            val dividerBottom = dividerTop + divider.intrinsicHeight
            divider.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom)
            divider.draw(canvas)
        }
    }
}