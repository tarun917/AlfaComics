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
import com.alfacomics.data.repository.DummyData
import com.alfacomics.pratilipitv.data.repository.Comic
import kotlin.collections.map

@Composable
fun ClassicTab(
    navController: NavHostController,
    comics: List<Comic> = DummyData.getAllComics(),
    modifier: Modifier = Modifier
) {
    val genres = comics.map { it.genre }.distinct()
    val groupedComics = genres.associateWith { genre ->
        comics.filter { it.genre == genre }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(groupedComics.entries.toList()) { entry ->
            val genre = entry.key
            val genreComics = entry.value
            ClassicComicScroll(
                genre = genre,
                comics = genreComics,
                navController = navController
            )
        }
    }
}