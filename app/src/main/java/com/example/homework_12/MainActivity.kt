package com.example.homework_12

import android.Manifest
import android.content.pm.PackageManager
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_12.adapter.ImageAdapter
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private var imageRecycler: RecyclerView? = null
    private val permissionCode: Int = 101
    private var allPictures: ArrayList<com.example.homework_12.model.Image>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageRecycler = findViewById(R.id.image_recycler)
        imageRecycler?.layoutManager = GridLayoutManager(this, 3)
        imageRecycler?.setHasFixedSize(true)

        if (ContextCompat.checkSelfPermission(
                this@MainActivity,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this@MainActivity, arrayOf(
                    Manifest
                        .permission.READ_EXTERNAL_STORAGE
                ), permissionCode
            )
        }
        allPictures = ArrayList()
        if (allPictures!!.isEmpty()) {
            allPictures = getAllImages()
            imageRecycler?.adapter = ImageAdapter(this, allPictures!!)
        }

    }

    private fun getAllImages(): ArrayList<com.example.homework_12.model.Image> {
        val images = ArrayList<com.example.homework_12.model.Image>()
        val allImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection =
            arrayOf(
                MediaStore.Images.ImageColumns.DATA,
                MediaStore.Images.Media.DISPLAY_NAME
            )
        var cursor =
            this@MainActivity
                .contentResolver
                .query(allImageUri, projection, null, null, null)

        try {
            cursor!!.moveToFirst()
            do {
                val image = com.example.homework_12.model.Image()
                image.imagePath =
                    cursor.getString(
                        cursor.getColumnIndexOrThrow(
                            MediaStore
                                .Images.Media.DATA
                        )
                    )
                image.imageName = cursor.getString(
                    cursor.getColumnIndexOrThrow(
                        MediaStore
                            .Images.Media.DISPLAY_NAME
                    )
                )
                images.add(image)
            } while (cursor.moveToNext())
            cursor.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return images
    }
}
