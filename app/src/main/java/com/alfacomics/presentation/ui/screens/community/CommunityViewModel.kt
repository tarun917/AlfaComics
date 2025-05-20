package com.alfacomics.presentation.ui.screens.community

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CommunityViewModel : ViewModel() {
    var selectedImageUrl by mutableStateOf<String?>(null)
        internal set
}