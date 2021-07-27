package com.shishkin.itransition.extensions

import android.view.View

fun View.makeVisible() {
    visibility = View.VISIBLE
}

fun View.makeGone() {
    visibility = View.GONE
}

fun View.makeVisibleOrGone(isVisible: Boolean) {
    if (isVisible) {
        makeVisible()
    } else {
        makeGone()
    }
}
