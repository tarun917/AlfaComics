package com.alfacomics.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.alfacomics.data.repository.AlfaStoreData

@Composable
fun HardCopyComicBox(
    title: String,
    coverImageUrl: String,
    price: Int,
    comicId: Int,
    rating: Float,
    modifier: Modifier = Modifier,
    navController: NavController? = null
) {
    val readCount = AlfaStoreData.getReadCountForHardCopyComic(comicId) // Updated to use AlfaStoreData

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Comic Cover Image with Overlay for Rating and Buyers
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                AsyncImage(
                    model = coverImageUrl,
                    contentDescription = "Comic Cover: $title",
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Gray)
                )

                // Left Top Corner: Number of Buyers
                Row(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(8.dp)
                        .background(Color.Black.copy(alpha = 0.7f), shape = MaterialTheme.shapes.small)
                        .padding(horizontal = 4.dp, vertical = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Number of Buyers",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = readCount.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White
                    )
                }

                // Right Top Corner: Rating
                Row(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .background(Color.Black.copy(alpha = 0.7f), shape = MaterialTheme.shapes.small)
                        .padding(horizontal = 4.dp, vertical = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Rating",
                        tint = Color.Yellow,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = rating.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Comic Title
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Price and Buy Button (Horizontally Aligned)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "₹$price",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )

                Button(
                    onClick = {
                        navController?.navigate("comic_purchase/$comicId")
                    },
                    modifier = Modifier
                        .width(80.dp) // Adjusted width for better alignment
                ) {
                    Text(text = "Buy")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}