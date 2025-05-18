package com.alfacomics.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun PaymentDialog(
    price: Int,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    var cardNumber by remember { mutableStateOf("") }
    var expiry by remember { mutableStateOf(TextFieldValue("")) }
    var cvv by remember { mutableStateOf("") }

    // Validate the expiry field (MM must be between 01 and 12)
    val isExpiryValid: Boolean
    val month: Int
    if (expiry.text.length == 5 && expiry.text[2] == '/') {
        month = expiry.text.substring(0, 2).toIntOrNull() ?: 0
        isExpiryValid = month in 1..12
    } else {
        month = 0
        isExpiryValid = false
    }

    val isFormValid = cardNumber.length == 16 && isExpiryValid && cvv.length == 3

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .widthIn(min = 300.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF121212))
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Title
                Text(
                    text = "Payment Details",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )

                // Amount
                Text(
                    text = "Amount: ₹$price",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )

                // Card Number
                OutlinedTextField(
                    value = cardNumber,
                    onValueChange = { if (it.length <= 16) cardNumber = it.filter { char -> char.isDigit() } },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    label = { Text("Card Number", color = Color.White) },
                    singleLine = true,
                    textStyle = LocalTextStyle.current.copy(color = Color.White),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color(0xFFBB86FC),
                        unfocusedIndicatorColor = Color.White.copy(alpha = 0.5f)
                    )
                )

                // Expiry and CVV Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Expiry
                    OutlinedTextField(
                        value = expiry,
                        onValueChange = { newValue ->
                            val filtered = newValue.text.filter { char -> char.isDigit() || char == '/' }
                            if (filtered.length <= 5) {
                                val digitsOnly = filtered.replace("/", "")
                                when (digitsOnly.length) {
                                    0 -> expiry = TextFieldValue("", selection = TextRange(0))
                                    1 -> expiry = TextFieldValue(digitsOnly, selection = TextRange(1))
                                    2 -> {
                                        // Automatically add "/" and move cursor past it
                                        val newText = "$digitsOnly/"
                                        expiry = TextFieldValue(newText, selection = TextRange(newText.length))
                                    }
                                    else -> {
                                        // Format as MM/YY
                                        val monthPart = digitsOnly.take(2)
                                        val yearPart = digitsOnly.drop(2).take(2)
                                        val newText = "$monthPart/$yearPart"
                                        expiry = TextFieldValue(newText, selection = TextRange(newText.length))
                                    }
                                }
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp),
                        label = { Text("MM/YY", color = Color.White) },
                        singleLine = true,
                        textStyle = LocalTextStyle.current.copy(color = Color.White),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color(0xFFBB86FC),
                            unfocusedIndicatorColor = Color.White.copy(alpha = 0.5f),
                            errorIndicatorColor = Color.Red
                        ),
                        isError = expiry.text.isNotEmpty() && !isExpiryValid
                    )

                    // CVV
                    OutlinedTextField(
                        value = cvv,
                        onValueChange = { if (it.length <= 3) cvv = it.filter { char -> char.isDigit() } },
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp),
                        label = { Text("CVV", color = Color.White) },
                        singleLine = true,
                        textStyle = LocalTextStyle.current.copy(color = Color.White),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color(0xFFBB86FC),
                            unfocusedIndicatorColor = Color.White.copy(alpha = 0.5f)
                        )
                    )
                }

                // Validation Error Message for Expiry
                if (expiry.text.isNotEmpty() && !isExpiryValid) {
                    Text(
                        text = "Month must be between 01 and 12",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Red,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }

                // Buttons Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancel", color = Color.White)
                    }
                    Button(
                        onClick = onConfirm,
                        enabled = isFormValid,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFBB86FC),
                            contentColor = Color.White,
                            disabledContainerColor = Color.Gray,
                            disabledContentColor = Color.White.copy(alpha = 0.5f)
                        )
                    ) {
                        Text("Pay Now")
                    }
                }
            }
        }
    }
}