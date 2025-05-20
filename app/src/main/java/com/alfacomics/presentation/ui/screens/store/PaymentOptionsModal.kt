package com.alfacomics.presentation.ui.screens.store

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.util.regex.Pattern
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentOptionsModal(
    onDismiss: () -> Unit,
    onPaymentSelected: (String) -> Unit
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    var showDebitCardModal by remember { mutableStateOf(false) }
    var upiId by remember { mutableStateOf("") }
    var phonePeNumber by remember { mutableStateOf("") }
    var googlePayNumber by remember { mutableStateOf("") }
    var paytmNumber by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Focus requesters for each text field
    val upiFocusRequester = remember { FocusRequester() }
    val phonePeFocusRequester = remember { FocusRequester() }
    val googlePayFocusRequester = remember { FocusRequester() }
    val paytmFocusRequester = remember { FocusRequester() }

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
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .imePadding() // Adjusts for keyboard height
                .padding(horizontal = 16.dp),
            state = listState,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Select Payment Methods",
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
            }

            // Payment Options
            // Debit Card
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showDebitCardModal = true }
                        .background(Color(0xFF1E1E1E), shape = MaterialTheme.shapes.medium)
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Debit Card",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Expand",
                        tint = Color.White
                    )
                }
            }

            // UPI
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF1E1E1E), shape = MaterialTheme.shapes.medium)
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "UPI",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        OutlinedTextField(
                            value = upiId,
                            onValueChange = { upiId = it },
                            placeholder = { Text("Enter UPI ID", color = Color.Gray) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .focusRequester(upiFocusRequester)
                                .focusable()
                                .onFocusChanged {
                                    if (it.isFocused) {
                                        coroutineScope.launch {
                                            listState.animateScrollToItem(1) // Scroll to UPI field
                                        }
                                    }
                                },
                            textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.White,
                                unfocusedBorderColor = Color.Gray,
                                focusedLabelColor = Color.White,
                                unfocusedLabelColor = Color.Gray
                            )
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(
                        onClick = {
                            errorMessage = validateUpiId(upiId)
                            if (errorMessage == null) {
                                onPaymentSelected("UPI")
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Confirm UPI",
                            tint = Color.Green
                        )
                    }
                }
            }

            // PhonePe
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF1E1E1E), shape = MaterialTheme.shapes.medium)
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "PhonePe",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        OutlinedTextField(
                            value = phonePeNumber,
                            onValueChange = { phonePeNumber = it },
                            placeholder = { Text("PhonePe Number", color = Color.Gray) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .focusRequester(phonePeFocusRequester)
                                .focusable()
                                .onFocusChanged {
                                    if (it.isFocused) {
                                        coroutineScope.launch {
                                            listState.animateScrollToItem(2) // Scroll to PhonePe field
                                        }
                                    }
                                },
                            textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.White,
                                unfocusedBorderColor = Color.Gray,
                                focusedLabelColor = Color.White,
                                unfocusedLabelColor = Color.Gray
                            )
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(
                        onClick = {
                            errorMessage = validatePhoneNumber(phonePeNumber, "PhonePe")
                            if (errorMessage == null) {
                                onPaymentSelected("PhonePe")
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Confirm PhonePe",
                            tint = Color.Green
                        )
                    }
                }
            }

            // GooglePay
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF1E1E1E), shape = MaterialTheme.shapes.medium)
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "GooglePay",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        OutlinedTextField(
                            value = googlePayNumber,
                            onValueChange = { googlePayNumber = it },
                            placeholder = { Text("GooglePay Number", color = Color.Gray) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .focusRequester(googlePayFocusRequester)
                                .focusable()
                                .onFocusChanged {
                                    if (it.isFocused) {
                                        coroutineScope.launch {
                                            listState.animateScrollToItem(3) // Scroll to GooglePay field
                                        }
                                    }
                                },
                            textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.White,
                                unfocusedBorderColor = Color.Gray,
                                focusedLabelColor = Color.White,
                                unfocusedLabelColor = Color.Gray
                            )
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(
                        onClick = {
                            errorMessage = validatePhoneNumber(googlePayNumber, "GooglePay")
                            if (errorMessage == null) {
                                onPaymentSelected("GooglePay")
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Confirm GooglePay",
                            tint = Color.Green
                        )
                    }
                }
            }

            // Paytm
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF1E1E1E), shape = MaterialTheme.shapes.medium)
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Paytm",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        OutlinedTextField(
                            value = paytmNumber,
                            onValueChange = { paytmNumber = it },
                            placeholder = { Text("Paytm Number", color = Color.Gray) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .focusRequester(paytmFocusRequester)
                                .focusable()
                                .onFocusChanged {
                                    if (it.isFocused) {
                                        coroutineScope.launch {
                                            listState.animateScrollToItem(4) // Scroll to Paytm field
                                        }
                                    }
                                },
                            textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.White,
                                unfocusedBorderColor = Color.Gray,
                                focusedLabelColor = Color.White,
                                unfocusedLabelColor = Color.Gray
                            )
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(
                        onClick = {
                            errorMessage = validatePhoneNumber(paytmNumber, "Paytm")
                            if (errorMessage == null) {
                                onPaymentSelected("Paytm")
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Confirm Paytm",
                            tint = Color.Green
                        )
                    }
                }
            }

            // Error Message (if any)
            item {
                errorMessage?.let { error ->
                    Text(
                        text = error,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    // Debit Card Details Modal
    if (showDebitCardModal) {
        DebitCardDetailsModal(
            onDismiss = { showDebitCardModal = false },
            onConfirm = { cardNumber, expiryDate, cvv ->
                // Simulate successful payment with Debit Card
                showDebitCardModal = false
                onPaymentSelected("Debit Card")
            }
        )
    }
}

private fun validateUpiId(upiId: String): String? {
    val upiPattern = Pattern.compile("^[a-zA-Z0-9._-]+@[a-zA-Z0-9]+$")
    return if (upiId.isBlank()) {
        "UPI ID is required"
    } else if (!upiPattern.matcher(upiId).matches()) {
        "Invalid UPI ID format (e.g., user@upi)"
    } else {
        null
    }
}

private fun validatePhoneNumber(phoneNumber: String, method: String): String? {
    val phonePattern = Pattern.compile("^[0-9]{10}$")
    return if (phoneNumber.isBlank()) {
        "$method Number is required"
    } else if (!phonePattern.matcher(phoneNumber).matches()) {
        "$method Number must be 10 digits"
    } else {
        null
    }
}