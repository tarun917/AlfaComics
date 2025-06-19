package com.alfacomics.presentation.ui.screens.store

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.alfacomics.data.repository.Review

@Composable
fun ReviewsSection(
    reviews: List<Review>,
    onReviewsClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF1E1E1E))
            .padding(8.dp)
    ) {
        Text(
            text = "Reviews (${reviews.size})",
            style = MaterialTheme.typography.titleMedium,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onReviewsClicked() }
                .padding(vertical = 8.dp)
        )
    }
}

@Composable
fun ReviewItem(review: Review) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF2A2A2A), shape = MaterialTheme.shapes.small)
            .padding(8.dp)
    ) {
        Text(
            text = review.buyerName,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White
        )
        Text(
            text = review.comment,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray,
            modifier = Modifier.padding(top = 4.dp)
        )
        Text(
            text = review.timestamp,
            style = MaterialTheme.typography.labelSmall,
            color = Color.Gray,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}