package ru.webant.notifications.utils

import android.view.View

fun View.changeVisibilityState(state: Boolean) {
    visibility = if (state) {
        View.VISIBLE
    } else {
        View.GONE
    }
}