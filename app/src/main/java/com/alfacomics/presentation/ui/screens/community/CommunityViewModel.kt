package com.alfacomics.presentation.ui.screens.community

import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CommunityViewModel : ViewModel() {
    var selectedImageUrl by mutableStateOf<String?>(null)

    private var imagePickerLauncher: ActivityResultLauncher<String>? = null

    fun setImagePickerLauncher(launcher: ActivityResultLauncher<String>?) {
        imagePickerLauncher = launcher
    }

    fun launchImagePicker() {
        imagePickerLauncher?.launch("image/*")
    }

    fun setSelectedImage(uri: String?) {
        selectedImageUrl = uri
    }
}