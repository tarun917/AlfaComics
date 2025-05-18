package com.alfacomics.presentation.ui.screens.favourite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.alfacomics.data.repository.DummyData
import com.alfacomics.presentation.ui.components.ComicBox

@Composable
fun FavouriteScreen(
    navController: NavHostController // Added navController parameter
) {
    val favoriteComics by remember { derivedStateOf { DummyData.getFavoriteComics() } }

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
                text = "Favourite Comics",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                textAlign = TextAlign.Center
            )
        }

        // List of Favorite Comics
        items(favoriteComics) { comic ->
            ComicBox(
                title = comic.title,
                coverImageUrl = comic.coverImageUrl,
                rating = comic.rating,
                comicId = comic.id,
                navController = navController // Pass navController to ComicBox
            )
        }

        // Empty State
        if (favoriteComics.isEmpty()) {
            item {
                Text(
                    text = "No favorite comics yet!",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}