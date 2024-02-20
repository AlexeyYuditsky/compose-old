package com.alexeyyuditsky.activityResultApiOld

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var getUsernameButton: Button
    private lateinit var usernameTextView: TextView
    private lateinit var getImageButton: Button
    private lateinit var imageFromGalleryImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()

        getUsernameButton.setOnClickListener {
            UsernameActivity.newIntent(this).apply {
                startActivityForResult(this, KEY_USERNAME)
            }

        }
        getImageButton.setOnClickListener {
            Intent(Intent.ACTION_PICK).apply {
                type = "image/*"
                startActivityForResult(this, KEY_GALLERY)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == KEY_USERNAME && resultCode == RESULT_OK) {
            val text = data?.getStringExtra(UsernameActivity.EXTRA_USERNAME) ?: ""
            usernameTextView.text = text
        } else if (requestCode == KEY_GALLERY && resultCode == RESULT_OK) {
            val uri = data?.data
            imageFromGalleryImageView.setImageURI(uri)
        }
    }

    private fun initViews() {
        getUsernameButton = findViewById(R.id.get_username_button)
        usernameTextView = findViewById(R.id.username_textview)
        getImageButton = findViewById(R.id.get_image_button)
        imageFromGalleryImageView = findViewById(R.id.image_from_gallery_imageview)
    }

    companion object {
        const val KEY_USERNAME = 100
        const val KEY_GALLERY = 101
    }
}