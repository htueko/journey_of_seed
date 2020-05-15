package com.htueko.android.journeyofseed.util

import android.app.Activity
import android.app.SearchManager
import android.content.Context
import android.net.ConnectivityManager
import android.util.DisplayMetrics
import androidx.core.content.ContextCompat

// to get the SearchManger
fun Context.getSearchManager() =
    getSystemService(Context.SEARCH_SERVICE) as SearchManager

// to get the connectivity manager
fun Context.getConnectivityManager() =
    getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

fun Activity.screenWidth(): Int {
    val metrics: DisplayMetrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(metrics)
    return metrics.widthPixels
}

fun Activity.screenHeight(): Int {
    val metrics: DisplayMetrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(metrics)
    return metrics.heightPixels
}

fun Activity.color(resId: Int): Int {
    return ContextCompat.getColor(this, resId)
}