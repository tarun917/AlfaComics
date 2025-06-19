package com.alfacomics.presentation.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.alfacomics.data.repository.MotionDummyData
import com.alfacomics.pratilipitv.data.repository.MotionComic

@Composable
fun ModernTab(
    navController: NavHostController,
    motionComics: List<MotionComic> = MotionDummyData.getMotionComics(), // Default to MotionDummyData for now
    modifier: Modifier = Modifier
) {
    val genres = motionComics.map { it.genre }.distinct()
    val groupedMotionComics = genres.associateWith { genre ->
        motionComics.filter { it.genre == genre }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(groupedMotionComics.entries.toList()) { entry ->
            ModernComicScroll(
                genre = entry.key,
                motionComics = entry.value,
                navController = navController
            )
        }
    }
}