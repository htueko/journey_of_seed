package com.htueko.android.journeyofseed.util

import android.app.Activity
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.htueko.android.journeyofseed.R

object PermissionObject {

    const val CAMERA_PERMISSION = android.Manifest.permission.CAMERA
    const val READ_EXTERNAL_STORAGE_PERMISSION = android.Manifest.permission.READ_EXTERNAL_STORAGE
    const val WRITE_EXTERNAL_STORAGE_PERMISSION = android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    const val ACCESS_COARSE_LOCATION_PERMISSION = android.Manifest.permission.ACCESS_COARSE_LOCATION

    // to check permission
    // return true if permission is granted
    // return false if permission is not granted (denied)
    private fun toCheckPermissions(activity: Activity, permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            activity.applicationContext,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    // to request runtime permissions
    private fun toRequestPermissions(
        activity: Activity,
        permissions: Array<String>,
        code: Int,
        message: String
    ) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions[0])) {
            // show the user about why he/she needed for this permission
            val title = activity.resources.getString(R.string.title_permission)
            AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(
                    activity.resources.getString(R.string.ok)
                ) { dialog, which ->
                    // show explanation then request
                    ActivityCompat.requestPermissions(activity, permissions, code)
                }
                .setNegativeButton(activity.resources.getString(R.string.cancel)) { dialog, which ->
                    dialog.dismiss()
                }.create().show()

        } else {
            // don't show explanation, just request
            ActivityCompat.requestPermissions(activity, permissions, code)
        }
    }

    // to request single permission and do the job after that
    fun toCheckAndRequestPermissions(
        activity: Activity,
        permissions: Array<String>,
        code: Int,
        message: String,
        onSuccess: () -> Unit
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // device's os version is 6.0 or above
            // needed to request runtime permission
            if (toCheckPermissions(activity, permissions[0])) {
                // permission is granted, do the job here
                onSuccess.invoke()
            } else {
                toRequestPermissions(
                    activity, permissions, code, message
                )
            }
        } else {
            // device's os version is lower than 6.0
            // don't need runtime permission request (already have one)
            onSuccess.invoke()
        }
    }

}