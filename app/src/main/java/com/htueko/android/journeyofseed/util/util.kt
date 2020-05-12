package com.htueko.android.journeyofseed.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.FileProvider
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
    var file: File? = null
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


// to take image with camera
private fun takeImageWithCamera(context: Context, code: Int) {
    try {
        val imageFile = createImageFile(context)
        if (imageFile != null) {
            var imagePath = imageFile.absolutePath
            // get the uri
            val imageUri = FileProvider.getUriForFile(
                context,
                "com.htueko.android.journeyofseed.provider",
                imageFile
            )
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
            (context as Activity).startActivityForResult(intent, code)
        } else {
            Toast.makeText(context, "Error while opening camera", Toast.LENGTH_LONG).show()
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
}