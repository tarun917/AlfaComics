package com.alfacomics.presentation.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.alfacomics.data.repository.DummyData
import com.alfacomics.presentation.ui.components.HorizontalComicScroll

@Composable
fun ClassicTab(
    navController: NavHostController
) {
    val comics = DummyData.getComicsByCategory("Classic")
    val genres = DummyData.getGenresByCategory("Classic")
    val groupedComics = genres.associateWith { genre ->
        comics.filter { it.genre == genre }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Header Item (Title)
        item {
            Text(
                text = "Comics", // Renamed from "Classic Comics" to "Comics"
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                textAlign = TextAlign.Center
            )
        }

        // Grouped Comics by Genre
        items(groupedComics.entries.toList()) { (genre, genreComics) ->
            Column {
                Text(
                    text = genre,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
                HorizontalComicScroll(
                    comics = genreComics,
                    navController = navController
                )
            }
        }
    }
}