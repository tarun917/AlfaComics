package com.alfacomics.presentation.ui.screens.search

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.alfacomics.data.repository.DummyData
import com.alfacomics.presentation.ui.components.ComicBox

@SuppressLint("UnrememberedMutableState")
@Composable
fun SearchScreen(
    navController: NavHostController // Added navController parameter
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedGenre by remember { mutableStateOf("") }
    val allComics = DummyData.getAllComics()
    val genres = listOf("") + DummyData.getGenresByCategory("Classic") + DummyData.getGenresByCategory("Modern")

    // Filter comics based on search query and genre
    val filteredComics by derivedStateOf {
        allComics.filter { comic ->
            val matchesQuery = searchQuery.isEmpty() || comic.title.contains(searchQuery, ignoreCase = true)
            val matchesGenre = selectedGenre.isEmpty() || comic.genre == selectedGenre
            matchesQuery && matchesGenre
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(horizontal = 16.dp)
    ) {
        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search Comics", color = Color.White) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon",
                    tint = Color.White
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = Color(0xFFBB86FC),
                unfocusedBorderColor = Color.Gray
            )
        )

        // Genre Filter Dropdown
        var expanded by remember { mutableStateOf(false) }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 16.dp)
        ) {
            OutlinedTextField(
                value = if (selectedGenre.isEmpty()) "All Genres" else selectedGenre,
                onValueChange = { /* Read-only */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF121212)),
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { expanded = true }) {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Dropdown",
                            tint = Color.White
                        )
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = Color(0xFFBB86FC),
                    unfocusedBorderColor = Color.Gray
                )
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF1E1E1E))
            ) {
                genres.forEach { genre ->
                    DropdownMenuItem(
                        text = { Text(if (genre.isEmpty()) "All Genres" else genre, color = Color.White) },
                        onClick = {
                            selectedGenre = genre
                            expanded = false
                        }
                    )
                }
            }
        }

        // Search Results
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filteredComics) { comic ->
                ComicBox(
                    title = comic.title,
                    coverImageUrl = comic.coverImageUrl,
                    rating = comic.rating,
                    comicId = comic.id,
                    navController = navController // Pass navController to ComicBox
                )
            }

            // Empty State
            if (filteredComics.isEmpty()) {
                item {
                    Text(
                        text = "No comics found!",
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
}