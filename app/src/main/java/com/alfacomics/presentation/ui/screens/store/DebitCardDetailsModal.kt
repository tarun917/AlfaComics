package com.alfacomics.presentation.ui.screens.store

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.util.regex.Pattern

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DebitCardDetailsModal(
    onDismiss: () -> Unit,
    onConfirm: (String, String, String) -> Unit
) {
    var cardNumber by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Create a ModalBottomSheetState to control the sheet's state
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true // Skip the partially expanded state
    )

    // Automatically expand the sheet when it opens
    LaunchedEffect(Unit) {
        sheetState.expand()
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = Color(0xFF121212)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Enter Debit Card Details",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )
                IconButton(onClick = onDismiss) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = Color.White
                    )
                }
            }

            // Card Number
            OutlinedTextField(
                value = cardNumber,
                onValueChange = { cardNumber = it },
                label = { Text("Card Number", color = Color.White) },
                modifier = Modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.Gray
                )
            )

            // Expiry Date
            OutlinedTextField(
                value = expiryDate,
                onValueChange = { expiryDate = it },
                label = { Text("Expiry Date (MM/YY)", color = Color.White) },
                modifier = Modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.Gray
                )
            )

            // CVV
            OutlinedTextField(
                value = cvv,
                onValueChange = { cvv = it },
                label = { Text("CVV", color = Color.White) },
                modifier = Modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.Gray
                )
            )

            // Error Message (if any)
            errorMessage?.let { error ->
                Text(
                    text = error,
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            // Confirm Button
            Button(
                onClick = {
                    errorMessage = validateDebitCardDetails(cardNumber, expiryDate, cvv)
                    if (errorMessage == null) {
                        onConfirm(cardNumber, expiryDate, cvv)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(text = "Confirm")
            }
        }
    }
}

private fun validateDebitCardDetails(cardNumber: String, expiryDate: String, cvv: String): String? {
    // Validate Card Number
    val cardPattern = Pattern.compile("^[0-9]{16}$")
    if (cardNumber.isBlank()) {
        return "Card Number is required"
    } else if (!cardPattern.matcher(cardNumber).matches()) {
        return "Card Number must be 16 digits"
    }

    // Validate Expiry Date (MM/YY format)
    val expiryPattern = Pattern.compile("^(0[1-9]|1[0-2])/([0-9]{2})$")
    if (expiryDate.isBlank()) {
        return "Expiry Date is required"
    } else if (!expiryPattern.matcher(expiryDate).matches()) {
        return "Expiry Date must be in MM/YY format"
    }

    // Validate CVV
    val cvvPattern = Pattern.compile("^[0-9]{3}$")
    if (cvv.isBlank()) {
        return "CVV is required"
    } else if (!cvvPattern.matcher(cvv).matches()) {
        return "CVV must be 3 digits"
    }

    return null // No errors
}