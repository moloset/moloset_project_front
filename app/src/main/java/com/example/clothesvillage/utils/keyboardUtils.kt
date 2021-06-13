package com.example.clothesvillage.utils

import android.app.Activity
import android.content.Context
import android.os.IBinder
import android.view.inputmethod.InputMethodManager

fun hideKeyboard(activiy: Activity?) {
    if (activiy == null) return
    val view = activiy.currentFocus

    view?.let {
        hideKeyboard(activiy, view.windowToken)
    }
}

fun hideKeyboard(activity: Activity?, windowToken: IBinder?) {
    if (activity == null) return
    val inputManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
}