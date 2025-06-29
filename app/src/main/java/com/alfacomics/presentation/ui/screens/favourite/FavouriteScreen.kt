package com.alfacomics.presentation.ui.screens.favourite

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.alfacomics.data.repository.DummyData
import com.alfacomics.data.repository.MotionDummyData
import com.alfacomics.pratilipitv.data.repository.Comic
import com.alfacomics.pratilipitv.data.repository.MotionComic

@Composable
fun FavouriteScreen(
    navController: NavHostController
) {
    val favoriteComics by remember { derivedStateOf { DummyData.getFavoriteComics() } }
    val favoriteMotionComics by remember {
        derivedStateOf { MotionDummyData.getMotionComics().filter { comic -> DummyData.isFavoriteMotionComic(comic.motion_comic_id) } }
    }
    val comicsListState = rememberLazyListState()
    val motionComicsListState = rememberLazyListState()

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Left Section: Comics
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            Text(
                text = "Comics",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
            if (favoriteComics.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxHeight(),
                    state = comicsListState,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(items = favoriteComics, key = { it.comic_id }) { comic ->
                        FavoriteComicItem(
                            comic = comic,
                            onClick = {
                                navController.navigate("comic_detail/${comic.comic_id}")
                            }
                        )
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No favorite comics yet.\nAdd some from the Home tab!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        // Right Section: Motion Comics
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            Text(
                text = "Motion Comics",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
            if (favoriteMotionComics.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxHeight(),
                    state = motionComicsListState,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(items = favoriteMotionComics, key = { it.motion_comic_id }) { motionComic ->
                        FavoriteMotionComicItem(
                            motionComic = motionComic,
                            onClick = {
                                navController.navigate("motion_comic_detail/${motionComic.motion_comic_id}")
                            }
                        )
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No favorite motion comics yet.\nAdd some from the Home tab!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun FavoriteComicItem(
    comic: Comic,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1E1E1E)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Placeholder for comic thumbnail
            Box(
                modifier = Modifier
                    .size(120.dp, 120.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.Gray),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Thumb",
                    color = Color.White,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            // Comic Title (below thumbnail)
            Text(
                text = comic.title,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun FavoriteMotionComicItem(
    motionComic: MotionComic,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1E1E1E)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Placeholder for motion comic thumbnail
            Box(
                modifier = Modifier
                    .size(120.dp, 120.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.Gray),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Thumb",
                    color = Color.White,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            // Motion Comic Title (below thumbnail)
            Text(
                text = motionComic.title,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}