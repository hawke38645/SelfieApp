package com.hawke38645.singleimageapplication

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var listPermissions: ArrayList<String> = ArrayList()
    private val MULTIPLE_REQUEST_CODE = 200
    private val GALLERY_REQUEST_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {
        btn_photo.setOnClickListener {
            checkForPermissions()
        }
        btn_gallery.setOnClickListener {
            checkForGalleryPermissions()
        }
        btn_open.setOnClickListener {
            var bottomSheetDialog = BottomSheet()
            bottomSheetDialog.show(supportFragmentManager, "BottomSheet")
        }
    }

    private fun checkForGalleryPermissions() {
        var permissions = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        listPermissions.clear()

        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    permission
                ) == PackageManager.PERMISSION_DENIED
            ) {
                listPermissions.add(permission)
                requestGalleryPermissions()
            } else {
                openGallery()
            }
        }
    }
    private fun checkForPermissions() {
        var permissions = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        listPermissions.clear()

        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED) {
                listPermissions.add(permission)
                requestPermissions()
            } else {
                openCamera()
            }
        }
    }

    private fun requestGalleryPermissions() {
        ActivityCompat.requestPermissions(
            this,
            listPermissions.toTypedArray(),
            GALLERY_REQUEST_CODE
        )
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            listPermissions.toTypedArray(),
            MULTIPLE_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            MULTIPLE_REQUEST_CODE -> {
                for (i in permissions.indices) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(applicationContext, "Permission Denied $i", Toast.LENGTH_SHORT)
                            .show()
                        return
                    }
                }
            }
        }
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, MULTIPLE_REQUEST_CODE)
    }

    private fun openGallery() {
        var intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode) {
            MULTIPLE_REQUEST_CODE -> {
                val captureImage = data!!.extras!!.get("data") as Bitmap
                image_view.setImageBitmap(captureImage)
            }
            GALLERY_REQUEST_CODE -> {
                    image_view.setImageURI(data?.data)
            }
        }
    }



}