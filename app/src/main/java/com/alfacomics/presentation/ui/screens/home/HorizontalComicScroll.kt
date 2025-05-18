package com.alfacomics.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.alfacomics.data.repository.Comic

@Composable
fun HorizontalComicScroll(
    comics: List<Comic>,
    navController: NavHostController
) {
    LazyRow(
        modifier = Modifier.padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(comics) { comic ->
            ComicBox(
                title = comic.title,
                coverImageUrl = comic.coverImageUrl,
                rating = comic.rating,
                comicId = comic.id,
                navController = navController
            )
        }
    }
}