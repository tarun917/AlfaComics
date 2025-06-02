package com.alfacomics.presentation.ui.screens.store

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.alfacomics.data.repository.AlfaStoreData
import com.alfacomics.data.repository.BuyerDetails
import com.alfacomics.data.repository.DummyData
import com.alfacomics.data.repository.Review
import kotlinx.coroutines.launch

@SuppressLint("UnusedBoxWithConstraintsScope")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComicPurchaseScreen(
    navController: NavController,
    comicId: Int
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val comic = remember { AlfaStoreData.getHardCopyComicById(comicId) }
    val userProfile = remember { DummyData.getUserProfile() }
    var purchaseError by remember { mutableStateOf<String?>(null) }
    var isPurchased by remember { mutableStateOf(DummyData.isComicPurchased(comicId)) }
    val readCount = AlfaStoreData.getReadCountForHardCopyComic(comicId) // Placeholder for total buyers
    var showDescriptionModal by remember { mutableStateOf(false) }
    var showReviewsModal by remember { mutableStateOf(false) }
    var showPaymentModal by remember { mutableStateOf(false) }
    var pendingBuyerDetails by remember { mutableStateOf<BuyerDetails?>(null) }

    if (comic == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF121212)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Comic not found",
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge
            )
        }
        return
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .imePadding() // Adjusts for keyboard height
            .padding(16.dp),
        state = listState,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Comic Cover Image
        item(key = "cover_image") {
            AsyncImage(
                model = comic.coverImageUrl,
                contentDescription = "Comic Cover: ${comic.title}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(Color.Gray)
            )
        }

        // Two-Column Layout for Details
        item(key = "details") {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Left Column: Description
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight() // Changed from wrapContentHeight to fillMaxHeight
                        .background(Color(0xFF1E1E1E), shape = MaterialTheme.shapes.medium)
                        .padding(8.dp)
                ) {
                    Text(
                        text = "Description",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    // Count words in the description
                    val wordCount = comic.description.split("\\s+".toRegex()).filter { it.isNotEmpty() }.size
                    val isLongDescription = wordCount > 20 // Threshold set to 15 words
                    Log.d("ComicPurchaseScreen", "Description Word Count: $wordCount, isLongDescription: $isLongDescription")
                    val truncatedDescription = if (isLongDescription) {
                        // Truncate to first 15 words for display
                        comic.description.split("\\s+".toRegex())
                            .filter { it.isNotEmpty() }
                            .take(20)
                            .joinToString(" ") + "..."
                    } else {
                        comic.description
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxHeight() // Ensure content takes full height
                    ) {
                        Text(
                            text = if (truncatedDescription.isEmpty()) "No description available." else truncatedDescription,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White,
                            maxLines = 8,
                            overflow = TextOverflow.Ellipsis
                        )
                        if (isLongDescription) {
                            Spacer(modifier = Modifier.height(4.dp)) // Add space before "More..."
                            Text(
                                text = "More...",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Cyan,
                                modifier = Modifier
                                    .clickable { showDescriptionModal = true }
                                    .padding(top = 0.dp)
                            )
                        }
                    }
                }

                // Right Column: Price, Pages, Ratings, Total Buyers, Reviews
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(Color(0xFF1E1E1E), shape = MaterialTheme.shapes.medium)
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Price
                    Text(
                        text = "Price: ₹${comic.price}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White
                    )

                    // Pages
                    Text(
                        text = "Pages: ${comic.pages}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White
                    )

                    // Ratings
                    Row(
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
                            text = "${comic.rating} (${comic.ratingsCount} users)",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White
                        )
                    }

                    // Total Buyers
                    Text(
                        text = "Total Buyers: $readCount",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White
                    )

                    // Reviews
                    ReviewsSection(
                        reviews = comic.reviews,
                        onReviewsClicked = { showReviewsModal = true }
                    )
                }
            }
        }

        // Purchase Form
        item(key = "purchase_form") {
            PurchaseForm(
                comicId = comicId,
                onBuyClicked = { buyerDetails ->
                    // Save buyer details
                    AlfaStoreData.saveBuyerDetails(buyerDetails)
                    // Store pending buyer details and show payment options modal
                    pendingBuyerDetails = buyerDetails
                    showPaymentModal = true
                    // Scroll to the top of the form to ensure visibility
                    coroutineScope.launch {
                        listState.animateScrollToItem(index = 2) // Adjust index based on your layout
                    }
                }
            )
        }

        // Purchase Error Message (if any)
        purchaseError?.let { error ->
            item(key = "purchase_error") {
                Text(
                    text = error,
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

        // Already Purchased Message
        if (isPurchased) {
            item(key = "already_purchased") {
                Text(
                    text = "You have already purchased this comic!",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Green,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }

    // Description Modal
    if (showDescriptionModal) {
        ModalBottomSheet(
            onDismissRequest = { showDescriptionModal = false },
            containerColor = Color(0xFF121212)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Description",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White
                    )
                    IconButton(onClick = { showDescriptionModal = false }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = if (comic.description.isEmpty()) "No description available." else comic.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    // Reviews Modal
    if (showReviewsModal) {
        ModalBottomSheet(
            onDismissRequest = { showReviewsModal = false },
            containerColor = Color(0xFF121212)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Reviews (${comic.reviews.size})",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White
                    )
                    IconButton(onClick = { showReviewsModal = false }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                if (comic.reviews.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 400.dp), // Increased max height for better visibility
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        itemsIndexed(comic.reviews) { _, review ->
                            ReviewItem(review = review)
                        }
                    }
                } else {
                    Text(
                        text = "No reviews yet.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    // Payment Options Modal
    if (showPaymentModal) {
        PaymentOptionsModal(
            onDismiss = { showPaymentModal = false },
            onPaymentSelected = { paymentMethod ->
                // For now, we'll simulate a successful payment
                if (comic.stockQuantity <= 0) {
                    purchaseError = "Out of stock!"
                    showPaymentModal = false
                    return@PaymentOptionsModal
                }
                if (userProfile.alfaCoins < comic.price) {
                    purchaseError = "Not enough AlfaCoins!"
                    showPaymentModal = false
                    return@PaymentOptionsModal
                }

                // Deduct AlfaCoins and confirm purchase
                val newBalance = userProfile.alfaCoins - comic.price
                DummyData.updateAlfaCoins(newBalance)
                DummyData.confirmPurchase(comicId)
                isPurchased = true
                purchaseError = null
                showPaymentModal = false
                // Clear the navigation stack to avoid duplicate screens
                navController.popBackStack("store", inclusive = false)
                navController.navigate("order_confirmation")
            }
        )
    }
}