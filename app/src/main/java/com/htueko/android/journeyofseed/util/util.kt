package com.htueko.android.journeyofseed.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

// show view
fun View.show() {
    this.visibility = View.VISIBLE
}

// hide view
fun View.hide() {
    this.visibility = View.INVISIBLE
}

// dismiss view
fun View.dismiss() {
    this.visibility = View.GONE
}

// show the keyboard
fun View.showKeyboard(activity: Activity) {
    val view = activity.findViewById<View>(android.R.id.content)
    val inputManager: InputMethodManager =
        activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.showSoftInput(view, 0)
}

// show the keyboard
fun View.showKeyboard(context: Context, view: View) {
    val inputManager: InputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.showSoftInput(view, 0)
}

// hide the keyboard
fun View.hideKeyboard(activity: Activity) {
    val view = activity.findViewById<View>(android.R.id.content)
    val inputManager: InputMethodManager =
        activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(view.windowToken, 0)
}

// hide the keyboard
fun View.hideKeyboard(context: Context, view: View) {
    val inputManager: InputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(view.windowToken, 0)
}
