package com.alexeyyuditsky.activityResultApiNew

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var getUsernameButton: Button
    private lateinit var usernameTextView: TextView
    private lateinit var getImageButton: Button
    private lateinit var imageFromGalleryImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()

        val contractUsername = ActivityResultContracts.StartActivityForResult()
        val launcherUsername = registerForActivityResult(contractUsername) { activityResult ->
            val userName = activityResult?.data?.getStringExtra(UsernameActivity.EXTRA_USERNAME)
            if (!userName.isNullOrEmpty())
                usernameTextView.text = userName
        }

        val contractImageUri = ActivityResultContracts.GetContent()
        val launcherImageUri = registerForActivityResult(contractImageUri) { uri ->
            if (uri != null)
                imageFromGalleryImageView.setImageURI(uri)
        }

        getUsernameButton.setOnClickListener {
            launcherUsername.launch(UsernameActivity.newIntent(this))
        }

        getImageButton.setOnClickListener {
            launcherImageUri.launch("image/*")
        }
    }

    private fun initViews() {
        getUsernameButton = findViewById(R.id.get_username_button)
        usernameTextView = findViewById(R.id.username_textview)
        getImageButton = findViewById(R.id.get_image_button)
        imageFromGalleryImageView = findViewById(R.id.image_from_gallery_imageview)
    }
}