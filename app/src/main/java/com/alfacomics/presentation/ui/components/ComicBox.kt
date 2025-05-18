package com.alfacomics.presentation.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.alfacomics.data.repository.DummyData
import java.text.DecimalFormat

@SuppressLint("UnrememberedMutableState")
@Composable
fun ComicBox(
    title: String,
    coverImageUrl: String,
    rating: Float,
    price: Int? = null,
    comicId: Int? = null,
    navController: NavHostController? = null // Added navController parameter
) {
    // State for purchase confirmation and dialog visibility
    val showPurchaseMessageState = remember { mutableStateOf(false) }
    var showPurchaseMessage by showPurchaseMessageState
    val showPaymentDialog = remember { mutableStateOf(false) }
    val isPurchased by derivedStateOf { comicId?.let { DummyData.isComicPurchased(it) } ?: false }

    // Get readCount if comicId is provided
    val readCount by derivedStateOf {
        comicId?.let { id ->
            DummyData.getComicById(id)?.readCount ?: 0
        } ?: 0
    }

    Column(
        modifier = Modifier
            .width(150.dp)
            .padding(8.dp)
            .clickable {
                // Navigate to ComicDetailScreen if comicId and navController are provided
                if (comicId != null && navController != null) {
                    navController.navigate("comic_detail/$comicId")
                }
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Cover Image with Overlay
        Box(
            modifier = Modifier
                .size(120.dp, 180.dp)
                .clip(RoundedCornerShape(8.dp))
        ) {
            // Placeholder for cover image
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Gray),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Cover\nPlaceholder",
                    color = Color.White,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center
                )
            }

            // Overlay for Rating (Top-Left)
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(4.dp)
                    .background(Color.Black.copy(alpha = 0.4f), RoundedCornerShape(4.dp))
                    .padding(horizontal = 4.dp, vertical = 2.dp)
            ) {
                Text(
                    text = "$rating ★",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Yellow,
                    textAlign = TextAlign.Center
                )
            }

            // Overlay for Eye Symbol and View Count (Top-Right)
            Row(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(4.dp)
                    .background(Color.Black.copy(alpha = 0.4f), RoundedCornerShape(4.dp))
                    .padding(horizontal = 4.dp, vertical = 2.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Eye Symbol
                Icon(
                    imageVector = Icons.Default.Visibility,
                    contentDescription = "View Count",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                // View Count
                Text(
                    text = formatReadCount(readCount),
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Comic Title
        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall,
            color = Color.White,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        // Price (if provided)
        if (price != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "₹$price",
                style = MaterialTheme.typography.labelMedium,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }

        // Buy Now Button (if price and comicId are provided and not purchased)
        if (price != null && comicId != null && !isPurchased) {
            Spacer(modifier = Modifier.height(4.dp))
            Button(
                onClick = {
                    showPaymentDialog.value = true // Show the mock payment dialog
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFBB86FC),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(36.dp)
            ) {
                Text(
                    text = "Buy Now",
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }

        // Purchase Confirmation Message
        if (showPurchaseMessage) {
            Text(
                text = "Purchase Confirmed!",
                style = MaterialTheme.typography.labelSmall,
                color = Color.Green,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }

    // Show Mock Payment Dialog
    if (showPaymentDialog.value) {
        MockPaymentDialog(
            onDismiss = { showPaymentDialog.value = false },
            onConfirm = {
                if (comicId != null) {
                    DummyData.confirmPurchase(comicId)
                    showPurchaseMessage = true
                }
                showPaymentDialog.value = false
            }
        )
    }
}

// Utility function to format read count (e.g., 1200 -> "1.2K")
private fun formatReadCount(count: Int): String {
    return when {
        count >= 1_000_000 -> "${DecimalFormat("#.#").format(count / 1_000_000.0)}M"
        count >= 1_000 -> "${DecimalFormat("#.#").format(count / 1_000.0)}K"
        else -> count.toString()
    }
}

@Composable
fun MockPaymentDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    // States for card details
    var cardNumber by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf(TextFieldValue("")) }
    var cvv by remember { mutableStateOf("") }
    var isFormValid by remember { mutableStateOf(false) }

    // Validate form whenever inputs change
    LaunchedEffect(cardNumber, expiryDate, cvv) {
        val expiryText = expiryDate.text
        isFormValid = cardNumber.length >= 16 && expiryText.length == 5 && expiryText[2] == '/' && cvv.length == 3
    }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Enter Payment Details",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Card Number Field
                OutlinedTextField(
                    value = cardNumber,
                    onValueChange = {
                        if (it.length <= 16 && it.all { char -> char.isDigit() }) cardNumber = it
                    },
                    label = { Text("Card Number", color = Color.White) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = Color(0xFFBB86FC),
                        unfocusedBorderColor = Color.Gray
                    )
                )

                // Expiry Date Field (MM/YY)
                OutlinedTextField(
                    value = expiryDate,
                    onValueChange = { newValue ->
                        val newText = newValue.text
                        if (newText.length <= 5) {
                            // Allow only digits and '/' at position 2
                            if (newText.all { char -> char.isDigit() || char == '/' }) {
                                when (newText.length) {
                                    2 -> {
                                        // Auto-add '/' after MM and move cursor to the right
                                        if (!newText.endsWith("/")) {
                                            expiryDate = TextFieldValue(
                                                text = "$newText/",
                                                selection = androidx.compose.ui.text.TextRange(3)
                                            )
                                        } else {
                                            expiryDate = newValue
                                        }
                                    }
                                    3 -> {
                                        // If user deletes '/' by backspacing, remove it
                                        if (newText.endsWith("/")) {
                                            expiryDate = TextFieldValue(
                                                text = newText.dropLast(1),
                                                selection = androidx.compose.ui.text.TextRange(2)
                                            )
                                        } else {
                                            expiryDate = newValue
                                        }
                                    }
                                    else -> {
                                        // Update the value, ensure '/' at position 2
                                        if (newText.length > 2 && newText[2] != '/') {
                                            val correctedText = newText.substring(0, 2) + "/" + newText.substring(3)
                                            expiryDate = TextFieldValue(
                                                text = correctedText,
                                                selection = androidx.compose.ui.text.TextRange(correctedText.length)
                                            )
                                        } else {
                                            expiryDate = newValue
                                        }
                                    }
                                }
                            }
                        }
                    },
                    label = { Text("MM/YY", color = Color.White) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = Color(0xFFBB86FC),
                        unfocusedBorderColor = Color.Gray
                    )
                )

                // CVV Field
                OutlinedTextField(
                    value = cvv,
                    onValueChange = {
                        if (it.length <= 3 && it.all { char -> char.isDigit() }) cvv = it
                    },
                    label = { Text("CVV", color = Color.White) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = Color(0xFFBB86FC),
                        unfocusedBorderColor = Color.Gray
                    )
                )

                // Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = onDismiss,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Gray,
                            contentColor = Color.White
                        )
                    ) {
                        Text("Cancel")
                    }
                    Button(
                        onClick = onConfirm,
                        enabled = isFormValid,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFBB86FC),
                            contentColor = Color.White
                        )
                    ) {
                        Text("Confirm Payment")
                    }
                }
            }
        }
    }
}