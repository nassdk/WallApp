package com.nassdk.wallapp.library.coreui.util

import android.view.View
import android.widget.LinearLayout
import com.google.android.material.tabs.TabLayout


fun View.makeVisible() {
    visibility = View.VISIBLE
}

fun View.makeGone() {
    visibility = View.GONE
}

fun View.makeInvisible() {
    visibility = View.INVISIBLE
}

fun View.isVisibleOrInvisible(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.INVISIBLE
}

fun View.isVisible(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

fun TabLayout.attachOnTabSelectedListener(tabSelected: (position: Int) -> Unit) {
    addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
        override fun onTabReselected(p0: TabLayout.Tab) = Unit
        override fun onTabUnselected(p0: TabLayout.Tab) = Unit
        override fun onTabSelected(tab: TabLayout.Tab) {
            tabSelected.invoke(tab.position)
        }
    })
}

fun TabLayout.toggleSelection(enabled: Boolean) {

    val tabStrip = getChildAt(0) as LinearLayout

    tabStrip.isEnabled = enabled

    for (i in 0 until tabStrip.childCount) {
        tabStrip.getChildAt(i).isClickable = enabled
    }
}