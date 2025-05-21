package com.alfacomics.presentation.ui.screens.store

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.alfacomics.data.repository.DummyData
import com.alfacomics.data.repository.HardCopyComic
import com.alfacomics.presentation.ui.components.HardCopyComicBox
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun AlfaStoreScreen(
    navController: NavHostController // Updated type to NavHostController
) {
    // State to hold the list of hard copy comics
    var hardCopyComics by remember { mutableStateOf<List<HardCopyComic>?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    // Load hard copy comics asynchronously
    LaunchedEffect(Unit) {
        isLoading = true
        hardCopyComics = withContext(Dispatchers.IO) {
            DummyData.getAllHardCopyComics()
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
        hardCopyComics?.let { comicList ->
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF121212))
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Header Item (Title and Order History Button)
                item(span = { GridItemSpan(2) }) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "AlfaStore - Hard Copies",
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center
                        )
                        IconButton(
                            onClick = {
                                navController.navigate("order_history")
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.History,
                                contentDescription = "Order History",
                                tint = Color.White
                            )
                        }
                    }
                }

                // Grid of Hard Copy Comics
                items(comicList) { comic ->
                    HardCopyComicBox(
                        title = comic.title,
                        coverImageUrl = comic.coverImageUrl,
                        price = comic.price,
                        rating = comic.rating, // Added rating parameter
                        comicId = comic.id,
                        navController = navController
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