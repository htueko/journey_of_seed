package com.htueko.android.journeyofseed.util

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.htueko.android.journeyofseed.data.adapter.PlantAdapter
import com.htueko.android.journeyofseed.data.adapter.SwipeToDelete
import com.htueko.android.journeyofseed.ui.viewmodel.PlantViewModel
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


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

// to check is there any camera or not in the device
fun isCameraAvailable(context: Context): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)
    } else {
        // VERSION.SDK_INT < JELLY_BEAN_MR1"
        return false
    }
}

// to get the local
fun getCurrentLocale(context: Context): Locale? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        context.resources.configuration.locales[0]
    } else {
        context.resources.configuration.locale
    }
}

// to create image file
fun createImageFile(context: Context): File? {
    var file: File?
    file = try {
        // Create an image file name
        val local = getCurrentLocale(context)
        val timeStamp: String = SimpleDateFormat("yyyy.MM.dd-hh:mm:ss aaa", local!!).format(Date())
        val mFileName = "JPEG_${timeStamp}_"
        val storageDirectory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val imageFile = File.createTempFile(mFileName, ".jpg", storageDirectory)
        imageFile
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
    return file
}

// to swipe left or right to delete from item from recycler view
fun swipeToDelete(
    adapter: PlantAdapter,
    viewModel: PlantViewModel,
    recyclerView: RecyclerView
) {
    val swipeHandler = object : SwipeToDelete() {
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            val plant = adapter.getPlantAt(position)
            viewModel.deletePlant(plant)
        }
    }
    val itemTouchHelper = ItemTouchHelper(swipeHandler)
    itemTouchHelper.attachToRecyclerView(recyclerView)
}

