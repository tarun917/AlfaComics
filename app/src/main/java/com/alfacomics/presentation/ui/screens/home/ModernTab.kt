package com.alfacomics.presentation.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.alfacomics.data.repository.MotionDummyData
import com.alfacomics.presentation.ui.components.HorizontalComicScroll

@Composable
fun ModernTab(
    navController: NavHostController
) {
    val genres = MotionDummyData.getGenres()
    val groupedMotionComics = genres.associateWith { genre ->
        MotionDummyData.getMotionComicsByGenre(genre)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 4.dp), // Already minimized in previous request
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Grouped Motion Comics by Genre
        items(groupedMotionComics.entries.toList()) { (genre, genreMotionComics) ->
            Column {
                Text(
                    text = genre,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 20.sp,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFFBB86FC), Color(0xFFFFD700))
                        )
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .shadow(2.dp, RoundedCornerShape(8.dp)),
                    textAlign = TextAlign.Start
                )
                HorizontalComicScroll(
                    items = genreMotionComics,
                    navController = navController,
                    modifier = Modifier, // Removed padding(horizontal = 4.dp) to minimize black space
                    onClick = { motionComic ->
                        navController.navigate("motion_comic_detail/${motionComic.id}")
                    }
                )
            }
        }
    }
}