package com.alfacomics.presentation.ui.screens.store

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.alfacomics.data.repository.Comic
import com.alfacomics.data.repository.DummyData
import com.alfacomics.presentation.ui.components.ComicBox
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun AlfaStoreScreen() {
    // State to hold the list of comics
    var comics by remember { mutableStateOf<List<Comic>?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    // Load comics asynchronously
    LaunchedEffect(Unit) {
        isLoading = true
        comics = withContext(Dispatchers.IO) {
            DummyData.getAllComics()
        }
        isLoading = false
    }

    if (isLoading) {
        // Show a loading indicator while fetching data
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF121212)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color.White)
        }
    } else {
        comics?.let { comicList ->
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF121212))
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Header Item (Title)
                item(span = { GridItemSpan(2) }) {
                    Text(
                        text = "AlfaStore - Hard Copies",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        textAlign = TextAlign.Center
                    )
                }

                // Grid of Comics
                items(comicList) { comic ->
                    ComicBox(
                        title = comic.title,
                        coverImageUrl = comic.coverImageUrl,
                        rating = comic.rating,
                        price = comic.price,
                        comicId = comic.id,
                    )
                }
            }
        } ?: run {
            // Fallback UI if comics fail to load
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF121212)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Failed to load comics",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}