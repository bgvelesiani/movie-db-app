package com.gvelesiani.movieapp.other.extensions

import android.view.View

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.toggleVisibility(): View {
    visibility = if (visibility == View.VISIBLE) {
        View.INVISIBLE
    } else {
        View.INVISIBLE
    }
    return this
}