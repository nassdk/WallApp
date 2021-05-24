package com.nassdk.wallapp.library.coreui.util

import android.view.View

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