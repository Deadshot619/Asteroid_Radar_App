package com.udacity.asteroidradar.utils

import android.app.Activity
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment

private var toast: Toast? = null

fun Activity.showToast(message: String, durationLong: Boolean = false){
    toast?.cancel()
    toast = Toast.makeText(this, message, if (durationLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT)
    toast?.show()
}

fun Fragment.showToast(message: String, durationLong: Boolean = false){
    activity?.showToast(message, durationLong)
}

fun View.visible(){
    this.visibility = View.VISIBLE
}

fun View.invisible(){
    this.visibility = View.INVISIBLE
}

fun View.gone(){
    this.visibility = View.GONE
}