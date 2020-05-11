package com.htueko.android.journeyofseed.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.htueko.android.journeyofseed.R
import com.htueko.android.journeyofseed.data.database.entity.PlantModel
import com.htueko.android.journeyofseed.ui.viewmodel.PlantViewModel
import com.htueko.android.journeyofseed.util.IMAGE_PICK_CODE
import com.htueko.android.journeyofseed.util.IMAGE_TAKE_CODE
import com.htueko.android.journeyofseed.util.PermissionObject
import com.htueko.android.journeyofseed.util.showKeyboard
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

        btn_save_insert.setOnClickListener {
            val name = textinput_name_insert.editText?.text.toString().trim()
            val location = textinput_location_insert.editText?.text.toString().trim()
            validate(name, location)
        }

        imv_image_insert.setOnClickListener {
            getImageFromGallery()
        }

        imv_camera_insert.setOnClickListener {
            checkAndRequestCamera()
        }

        btn_cancel_insert.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        }

    }

    // to get the return result (here is photo from gallery)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (resultCode) {
                IMAGE_PICK_CODE -> {
                    // get image from gallery
                    val imageUri = data?.data
                    plantModel.localUrl = imageUri.toString()
                    Glide.with(this).load(imageUri).into(imv_image_insert)
                }
                IMAGE_TAKE_CODE -> {
                    // take image with camera
                    val imageUri = data?.data
                    plantModel.localUrl = imageUri.toString()
                    Glide.with(this).load(imageUri).into(imv_image_insert)
                }
                else -> {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                }
            }
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

    // to get image from Gallery
    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    // take picture with Camera
    private fun takeImageWithCamera() {
        // intent to take image
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, IMAGE_TAKE_CODE)
    }

    // request to get image from gallery
    private fun getImageFromGallery() {
        PermissionObject.singlePermissionCheckAndRequest(
            activity = this@InsertActivity,
            permission = PermissionObject.READ_EXTERNAL_STORAGE_PERMISSION,
            code = IMAGE_PICK_CODE,
            message = resources.getString(R.string.pick_picture_explanation),
            onSuccess = ::pickImageFromGallery
        )
    }

    // check camera permission and request then take the picture with camera
    private fun checkAndRequestCamera() {
        PermissionObject.multiplePermissionCheckAndRequest(
            activity = this@InsertActivity,
            permissions = arrayOf(PermissionObject.CAMERA_PERMISSION),
            code = IMAGE_TAKE_CODE,
            message = resources.getString(R.string.pick_picture_explanation),
            onSuccess = ::takeAndGetImageWithCamera
        )
    }

    // request to take image with camera
    private fun takeAndGetImageWithCamera() {
        PermissionObject.singlePermissionCheckAndRequest(
            activity = this@InsertActivity,
            permission = PermissionObject.WRITE_EXTERNAL_STORAGE_PERMISSION,
            code = IMAGE_TAKE_CODE,
            message = resources.getString(R.string.take_camera_explanation),
            onSuccess = ::takeImageWithCamera
        )
    }

}
