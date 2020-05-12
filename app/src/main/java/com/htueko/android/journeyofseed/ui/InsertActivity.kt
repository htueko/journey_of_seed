package com.htueko.android.journeyofseed.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.htueko.android.journeyofseed.R
import com.htueko.android.journeyofseed.data.database.entity.PlantModel
import com.htueko.android.journeyofseed.ui.viewmodel.PlantViewModel
import com.htueko.android.journeyofseed.util.*
import kotlinx.android.synthetic.main.activity_insert.*


class InsertActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this).get(PlantViewModel::class.java)
    }

    // to get the plant properties from user (title, location and image uri)
    private lateinit var plantModel: PlantModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert)

        plantModel = PlantModel("", "", "")

        // check whether the camera is available or not
        val isCameraAvailable = isCameraAvailable(this)
        // if camera is not available then hide the camera icon
        if (!isCameraAvailable) {
            imv_camera_insert.hide()
        } else {
            imv_camera_insert.setOnClickListener {
                Log.d("TAG btn: ", "Button clicked")
                requestAndTakeImageWithCamera()
            }
        }

        btn_save_insert.setOnClickListener {
            val name = textinput_name_insert.editText?.text.toString().trim()
            val location = textinput_location_insert.editText?.text.toString().trim()
            validate(name, location)
        }

        imv_image_insert.setOnClickListener {
            requestAndGetImageFromGallery()
        }

        btn_cancel_insert.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        }

    }

    // to get the return result (here is photo from gallery)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            Log.d("TAG result: ", "$resultCode")
            Log.d("TAG request: ", "$requestCode")
            when (requestCode) {
                IMAGE_PICK_CODE -> {
                    // get image from gallery
                    val imageUri = data?.data
                    Log.d("TAG IPC:", "$imageUri")
                    plantModel.localUrl = imageUri.toString()
                    Glide.with(this).load(imageUri).into(imv_image_insert)
                }
                IMAGE_TAKE_CODE -> {
                    // get the image from camera
                    val captureImage = data?.extras?.get("data") as Bitmap

                    Log.d("TAG IPC bitmap:", "$captureImage")
                    Log.d("TAG IPC: string", "$captureImage")
                    plantModel.localUrl = captureImage.toString()
                    // set image to imageview
                    imv_image_insert.setImageBitmap(captureImage)
                }
                else -> {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }


    // to validate the user input and act according to result
    private fun validate(name: String, location: String) {
        if (name.isNotEmpty() && location.isNotEmpty()) {
            // input are not empty
            // add name and location to plantModel we declare at the top
            plantModel.name = name
            plantModel.location = location
            if (plantModel.localUrl == "") {
                // show alert the user about lacking the image
                plantModel.localUrl = ""
            }
            viewModel.insertOrUpdate(plantModel)
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        } else {
            // one input is empty
            // check name first
            if (name.isEmpty()) {
                textinput_name_insert.apply {
                    error = resources.getString(R.string.text_name_error)
                    requestFocus()
                    showKeyboard(this@InsertActivity)
                }
            } else {
                // name is not empty, so location must be empty
                textinput_location_insert.apply {
                    error = resources.getString(R.string.text_location_error)
                    requestFocus()
                    showKeyboard(this@InsertActivity)
                }
            }
        }

    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, IMAGE_TAKE_CODE)
            }
        }
    }


    // request to get image from gallery
    private fun requestAndGetImageFromGallery() {
        PermissionObject.toCheckAndRequestPermissions(
            activity = this@InsertActivity,
            permissions = arrayOf(PermissionObject.READ_EXTERNAL_STORAGE_PERMISSION),
            code = IMAGE_PICK_CODE,
            message = resources.getString(R.string.pick_picture_explanation),
            onSuccess = ::pickImageFromGallery
        )
    }

    // to get image from Gallery
    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    // request to take image from camera
    private fun requestAndTakeImageWithCamera() {
        Log.d("TAG btn: ", "requestandtakeimage")
        PermissionObject.toCheckAndRequestPermissions(
            activity = this@InsertActivity,
            permissions = arrayOf(PermissionObject.CAMERA_PERMISSION),
            code = IMAGE_TAKE_CODE,
            message = resources.getString(R.string.take_camera_explanation),
            onSuccess = ::takeImageWithCamera
        )
    }

    // to take the image with camera
    private fun takeImageWithCamera() {
        Log.d("TAG btn: ", "open camera")
        // open camera
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, IMAGE_TAKE_CODE)
    }


}
