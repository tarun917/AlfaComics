package com.alfacomics.presentation.ui.screens.community

import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.compositionLocalOf
import androidx.activity.result.ActivityResultLauncher

val LocalActivityResultLauncher = compositionLocalOf<ActivityResultLauncher<String>?> { null }