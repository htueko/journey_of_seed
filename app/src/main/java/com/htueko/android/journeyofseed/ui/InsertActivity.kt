package com.htueko.android.journeyofseed.ui

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.htueko.android.journeyofseed.R
import com.htueko.android.journeyofseed.data.database.entity.PlantModel
import com.htueko.android.journeyofseed.ui.viewmodel.PlantViewModel
import com.htueko.android.journeyofseed.util.*
import kotlinx.android.synthetic.main.activity_insert.*
import java.io.File


class InsertActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this).get(PlantViewModel::class.java)
    }

    // to get the plant properties from user (title, location and image uri)
    private lateinit var plantModel: PlantModel

    // to get the high resolution image from camera
    private lateinit var imageFile: File

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
            when (requestCode) {
                IMAGE_PICK_CODE -> {
                    // get image from gallery
                    val imageUri = data?.data
                    plantModel.localUrl = imageUri.toString()
                    Glide.with(this).load(imageUri).into(imv_image_insert)
                }
                IMAGE_TAKE_CODE -> {
                    // get the image from camera
                    val imageTaken = BitmapFactory.decodeFile(imageFile.absolutePath)
                    val imagePath = imageFile.absolutePath
                    plantModel.localUrl = imagePath
                    Glide.with(this).load(imageTaken).into(imv_image_insert)
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
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        val mimeTypes = arrayOf("image/jpeg", "image/png", "image/jpg")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        }
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        intent.also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(intent, IMAGE_PICK_CODE)
            }
        }
    }

    // request to take image from camera
    private fun requestAndTakeImageWithCamera() {
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
        imageFile = createImageFile(this)!!
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val fileProvider = FileProvider.getUriForFile(
            this,
            "com.htueko.android.journeyofseed.fileprovider",
            imageFile
        )
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)
        intent.also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(intent, IMAGE_TAKE_CODE)
            }
        }
    }

}
