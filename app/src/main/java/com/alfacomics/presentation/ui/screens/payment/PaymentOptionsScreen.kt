package com.alfacomics.presentation.ui.screens.payment

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import androidx.compose.ui.res.painterResource
import com.alfacomics.R

@Composable
fun PaymentOptionsScreen(
    navController: NavHostController,
    planDuration: String,
    price: String,
    onPaymentSuccess: () -> Unit,
    onPaymentFailed: () -> Unit
) {
    var selectedMethod by remember { mutableStateOf<String?>(null) }
    var showPaymentDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Credit/Debit Card Details
    var showCardDetailsDialog by remember { mutableStateOf(false) }
    var cardNumber by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    var cardHolderName by remember { mutableStateOf("") }

    // UPI Details
    var showUPIDetailsDialog by remember { mutableStateOf(false) }
    var selectedUPIApp by remember { mutableStateOf("PhonePe") } // Default to PhonePe
    var upiId by remember { mutableStateOf("") }
    var mobileNumber by remember { mutableStateOf("") }
    var inputType by remember { mutableStateOf("Enter UPI ID") } // Default to UPI ID
    var showInputTypeDropdown by remember { mutableStateOf(false) }

    // Scan QR
    var showQRDialog by remember { mutableStateOf(false) }

    // Gradient background for the screen
    val gradientBackground = Brush.verticalGradient(
        colors = listOf(Color(0xFF1A0033), Color(0xFF121212)),
        startY = 0f,
        endY = 1000f
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBackground)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Back Button and Title
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    navController.navigateUp()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Select Payment Method",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontSize = 28.sp,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFFBB86FC), Color(0xFFFFD700))
                    )
                ),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

        // Plan Details
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .border(
                    width = 2.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFFBB86FC), Color(0xFFFFD700))
                    ),
                    shape = RoundedCornerShape(16.dp)
                )
                .background(
                    Brush.linearGradient(
                        colors = listOf(Color(0xFF2A004D), Color(0xFF1C2526))
                    ),
                    shape = RoundedCornerShape(16.dp)
                ),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Plan: $planDuration",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Amount: $price",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Payment Methods
        Text(
            text = "Choose a Payment Method",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFFBB86FC), Color(0xFFFFD700))
                )
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            textAlign = TextAlign.Start
        )

        PaymentMethodCard(
            methodName = "Credit/Debit Card",
            isSelected = selectedMethod == "Credit/Debit Card",
            onClick = {
                selectedMethod = "Credit/Debit Card"
                showCardDetailsDialog = true
            }
        )
        PaymentMethodCard(
            methodName = "UPI (PhonePe, Google Pay, Paytm)",
            isSelected = selectedMethod == "UPI",
            onClick = {
                selectedMethod = "UPI"
                showUPIDetailsDialog = true
            }
        )
        PaymentMethodCard(
            methodName = "Scan QR",
            isSelected = selectedMethod == "Scan QR",
            onClick = {
                selectedMethod = "Scan QR"
                showQRDialog = true
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Error Message (if any)
        errorMessage?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = it,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Pay Now Button
        Button(
            onClick = {
                when (selectedMethod) {
                    "Credit/Debit Card" -> {
                        if (cardNumber.isBlank() || expiryDate.isBlank() || cvv.isBlank() || cardHolderName.isBlank()) {
                            errorMessage = "Please enter all card details"
                            return@Button
                        }
                    }
                    "UPI" -> {
                        if (selectedUPIApp == null) {
                            errorMessage = "Please select a UPI app"
                            return@Button
                        }
                        if (inputType == "Enter UPI ID" && upiId.isBlank()) {
                            errorMessage = "Please enter UPI ID"
                            return@Button
                        }
                        if (inputType == "Enter Mobile Number" && mobileNumber.isBlank()) {
                            errorMessage = "Please enter mobile number"
                            return@Button
                        }
                    }
                    "Scan QR" -> {
                        // No additional validation needed for QR code
                    }
                    else -> {
                        errorMessage = "Please select a payment method"
                        return@Button
                    }
                }
                errorMessage = null
                showPaymentDialog = true
            },
            enabled = selectedMethod != null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.White,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.White.copy(alpha = 0.5f)
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        if (selectedMethod != null) {
                            Brush.linearGradient(
                                colors = listOf(Color(0xFFBB86FC), Color(0xFFFFD700))
                            )
                        } else {
                            Brush.linearGradient(
                                colors = listOf(Color.Gray, Color.Gray)
                            )
                        },
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Pay Now",
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 16.sp)
                )
            }
        }
    }

    // Credit/Debit Card Details Dialog
    if (showCardDetailsDialog) {
        Dialog(onDismissRequest = { showCardDetailsDialog = false }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .border(
                        width = 2.dp,
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFFBB86FC), Color(0xFFFFD700))
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Enter Card Details",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )

                    OutlinedTextField(
                        value = cardNumber,
                        onValueChange = { cardNumber = it },
                        label = { Text("Card Number", color = Color.White) },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = LocalTextStyle.current.copy(color = Color.White),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color(0xFFBB86FC),
                            unfocusedIndicatorColor = Color.White.copy(alpha = 0.5f)
                        )
                    )

                    OutlinedTextField(
                        value = expiryDate,
                        onValueChange = { expiryDate = it },
                        label = { Text("Expiry Date (MM/YY)", color = Color.White) },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = LocalTextStyle.current.copy(color = Color.White),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color(0xFFBB86FC),
                            unfocusedIndicatorColor = Color.White.copy(alpha = 0.5f)
                        )
                    )

                    OutlinedTextField(
                        value = cvv,
                        onValueChange = { cvv = it },
                        label = { Text("CVV", color = Color.White) },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = LocalTextStyle.current.copy(color = Color.White),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color(0xFFBB86FC),
                            unfocusedIndicatorColor = Color.White.copy(alpha = 0.5f)
                        )
                    )

                    OutlinedTextField(
                        value = cardHolderName,
                        onValueChange = { cardHolderName = it },
                        label = { Text("Cardholder Name", color = Color.White) },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = LocalTextStyle.current.copy(color = Color.White),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color(0xFFBB86FC),
                            unfocusedIndicatorColor = Color.White.copy(alpha = 0.5f)
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = { showCardDetailsDialog = false },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Gray,
                                contentColor = Color.White
                            )
                        ) {
                            Text("Cancel")
                        }
                        Button(
                            onClick = {
                                if (cardNumber.isBlank() || expiryDate.isBlank() || cvv.isBlank() || cardHolderName.isBlank()) {
                                    errorMessage = "Please enter all card details"
                                    return@Button
                                }
                                errorMessage = null
                                showCardDetailsDialog = false
                                showPaymentDialog = true
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                contentColor = Color.White
                            ),
                            modifier = Modifier
                                .background(
                                    Brush.linearGradient(
                                        colors = listOf(Color(0xFFBB86FC), Color(0xFFFFD700))
                                    ),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Text("Pay")
                        }
                    }
                }
            }
        }
    }

    // UPI Details Dialog
    if (showUPIDetailsDialog) {
        Dialog(onDismissRequest = { showUPIDetailsDialog = false }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .border(
                        width = 2.dp,
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFFBB86FC), Color(0xFFFFD700))
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Select UPI App",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )

                    // UPI App Options (PhonePe, Google Pay, Paytm) with Icons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        UPIAppOption(
                            appIconRes = R.drawable.phonepe_logo,
                            appName = "PhonePe",
                            isSelected = selectedUPIApp == "PhonePe",
                            onClick = { selectedUPIApp = "PhonePe" }
                        )
                        UPIAppOption(
                            appIconRes = R.drawable.googlepay_logo,
                            appName = "Google Pay",
                            isSelected = selectedUPIApp == "Google Pay",
                            onClick = { selectedUPIApp = "Google Pay" }
                        )
                        UPIAppOption(
                            appIconRes = R.drawable.paytm_logo,
                            appName = "Paytm",
                            isSelected = selectedUPIApp == "Paytm",
                            onClick = { selectedUPIApp = "Paytm" }
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Input Type Dropdown and Field
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 8.dp)
                        ) {
                            if (inputType == "Enter UPI ID") {
                                OutlinedTextField(
                                    value = upiId,
                                    onValueChange = { upiId = it },
                                    label = { Text("Enter UPI ID", color = Color.White) },
                                    modifier = Modifier.fillMaxWidth(),
                                    textStyle = LocalTextStyle.current.copy(color = Color.White),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                                    colors = TextFieldDefaults.colors(
                                        focusedContainerColor = Color.Transparent,
                                        unfocusedContainerColor = Color.Transparent,
                                        focusedIndicatorColor = Color(0xFFBB86FC),
                                        unfocusedIndicatorColor = Color.White.copy(alpha = 0.5f)
                                    )
                                )
                            } else {
                                OutlinedTextField(
                                    value = mobileNumber,
                                    onValueChange = { mobileNumber = it },
                                    label = { Text("Enter Mobile Number", color = Color.White) },
                                    modifier = Modifier.fillMaxWidth(),
                                    textStyle = LocalTextStyle.current.copy(color = Color.White),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                                    colors = TextFieldDefaults.colors(
                                        focusedContainerColor = Color.Transparent,
                                        unfocusedContainerColor = Color.Transparent,
                                        focusedIndicatorColor = Color(0xFFBB86FC),
                                        unfocusedIndicatorColor = Color.White.copy(alpha = 0.5f)
                                    )
                                )
                            }
                        }

                        // Input Type Dropdown (Only Icon)
                        Box {
                            IconButton(onClick = { showInputTypeDropdown = true }) {
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = "Select Input Type",
                                    tint = Color.White
                                )
                            }
                            DropdownMenu(
                                expanded = showInputTypeDropdown,
                                onDismissRequest = { showInputTypeDropdown = false },
                                modifier = Modifier
                                    .background(Color(0xFF2A004D))
                                    .border(
                                        width = 1.dp,
                                        brush = Brush.linearGradient(
                                            colors = listOf(Color(0xFFBB86FC), Color(0xFFFFD700))
                                        ),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .width(180.dp)
                            ) {
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            "Enter UPI ID",
                                            color = Color.White,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .background(
                                                    if (inputType == "Enter UPI ID") {
                                                        Brush.linearGradient(
                                                            colors = listOf(Color(0xFFBB86FC).copy(alpha = 0.2f), Color(0xFFFFD700).copy(alpha = 0.2f))
                                                        )
                                                    } else {
                                                        Brush.linearGradient(
                                                            colors = listOf(Color.Transparent, Color.Transparent)
                                                        )
                                                    },
                                                    shape = RoundedCornerShape(4.dp)
                                                )
                                                .padding(8.dp),
                                            textAlign = TextAlign.Center
                                        )
                                    },
                                    onClick = {
                                        inputType = "Enter UPI ID"
                                        showInputTypeDropdown = false
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                )
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            "Enter Mobile Number",
                                            color = Color.White,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .background(
                                                    if (inputType == "Enter Mobile Number") {
                                                        Brush.linearGradient(
                                                            colors = listOf(Color(0xFFBB86FC).copy(alpha = 0.2f), Color(0xFFFFD700).copy(alpha = 0.2f))
                                                        )
                                                    } else {
                                                        Brush.linearGradient(
                                                            colors = listOf(Color.Transparent, Color.Transparent)
                                                        )
                                                    },
                                                    shape = RoundedCornerShape(4.dp)
                                                )
                                                .padding(8.dp),
                                            textAlign = TextAlign.Center
                                        )
                                    },
                                    onClick = {
                                        inputType = "Enter Mobile Number"
                                        showInputTypeDropdown = false
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = { showUPIDetailsDialog = false },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Gray,
                                contentColor = Color.White
                            )
                        ) {
                            Text("Cancel")
                        }
                        Button(
                            onClick = {
                                if (selectedUPIApp == null) {
                                    errorMessage = "Please select a UPI app"
                                    return@Button
                                }
                                if (inputType == "Enter UPI ID" && upiId.isBlank()) {
                                    errorMessage = "Please enter UPI ID"
                                    return@Button
                                }
                                if (inputType == "Enter Mobile Number" && mobileNumber.isBlank()) {
                                    errorMessage = "Please enter mobile number"
                                    return@Button
                                }
                                errorMessage = null
                                showUPIDetailsDialog = false
                                showPaymentDialog = true
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                contentColor = Color.White
                            ),
                            modifier = Modifier
                                .background(
                                    Brush.linearGradient(
                                        colors = listOf(Color(0xFFBB86FC), Color(0xFFFFD700))
                                    ),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Text("Pay")
                        }
                    }
                }
            }
        }
    }

    // Scan QR Dialog
    if (showQRDialog) {
        Dialog(onDismissRequest = { showQRDialog = false }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .border(
                        width = 2.dp,
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFFBB86FC), Color(0xFFFFD700))
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Scan QR Code to Pay",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )

                    // Plan Details
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(Color(0xFF2A004D), Color(0xFF1C2526))
                                ),
                                shape = RoundedCornerShape(12.dp)
                            ),
                        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Plan: $planDuration",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "Amount: $price",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Dummy QR Code
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(Color(0xFF2A004D), Color(0xFF1C2526))
                                ),
                                shape = RoundedCornerShape(12.dp)
                            ),
                        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "[Dummy QR Code Placeholder]",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White.copy(alpha = 0.7f),
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = { showQRDialog = false },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Gray,
                                contentColor = Color.White
                            )
                        ) {
                            Text("Cancel")
                        }
                        Button(
                            onClick = {
                                showQRDialog = false
                                showPaymentDialog = true
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                contentColor = Color.White
                            ),
                            modifier = Modifier
                                .background(
                                    Brush.linearGradient(
                                        colors = listOf(Color(0xFFBB86FC), Color(0xFFFFD700))
                                    ),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Text("Pay")
                        }
                    }
                }
            }
        }
    }

    // Payment Processing Dialog
    if (showPaymentDialog) {
        var paymentProgress by remember { mutableStateOf(false) }
        var paymentFailed by remember { mutableStateOf(false) }
        LaunchedEffect(Unit) {
            paymentProgress = true
            delay(2000) // Simulate payment processing delay
            paymentProgress = false
            // Simulate payment success/failure (randomize for testing)
            val success = PaymentProcessor.processPayment(selectedMethod!!, price)
            if (success) {
                onPaymentSuccess()
                navController.navigate("home") {
                    popUpTo("payment") { inclusive = true }
                }
            } else {
                paymentFailed = true
            }
        }

        AlertDialog(
            onDismissRequest = { if (!paymentProgress) showPaymentDialog = false },
            title = {
                Text(
                    text = when {
                        paymentProgress -> "Processing Payment..."
                        paymentFailed -> "Payment Failed!"
                        else -> "Payment Successful!"
                    },
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFFBB86FC), Color(0xFFFFD700))
                        )
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (paymentProgress) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(48.dp),
                            color = Color(0xFFBB86FC)
                        )
                    } else if (paymentFailed) {
                        Text(
                            text = "Payment failed. Please try again.",
                            color = Color.White.copy(alpha = 0.7f),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    } else {
                        Text(
                            text = "Your payment of $price for the $planDuration plan was successful!",
                            color = Color.White.copy(alpha = 0.7f),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            },
            confirmButton = {
                if (!paymentProgress) {
                    if (paymentFailed) {
                        Button(
                            onClick = {
                                showPaymentDialog = false
                                onPaymentFailed()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                contentColor = Color.White
                            ),
                            modifier = Modifier
                                .background(
                                    Brush.linearGradient(
                                        colors = listOf(Color(0xFFBB86FC), Color(0xFFFFD700))
                                    ),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Text("Try Again", fontSize = 16.sp)
                        }
                    } else {
                        Button(
                            onClick = {
                                showPaymentDialog = false
                                navController.navigate("home") {
                                    popUpTo("payment") { inclusive = true }
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                contentColor = Color.White
                            ),
                            modifier = Modifier
                                .background(
                                    Brush.linearGradient(
                                        colors = listOf(Color(0xFFBB86FC), Color(0xFFFFD700))
                                    ),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Text("Continue", fontSize = 16.sp)
                        }
                    }
                }
            },
            containerColor = Color(0xFF1E1E1E),
            modifier = Modifier
                .border(
                    width = 2.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFFBB86FC), Color(0xFFFFD700))
                    ),
                    shape = RoundedCornerShape(16.dp)
                )
        )
    }
}

@Composable
fun PaymentMethodCard(
    methodName: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() }
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                brush = Brush.linearGradient(
                    colors = if (isSelected) listOf(Color(0xFFFFD700), Color(0xFFBB86FC))
                    else listOf(Color(0xFFBB86FC), Color(0xFF6200EE))
                ),
                shape = RoundedCornerShape(12.dp)
            )
            .background(
                Brush.linearGradient(
                    colors = listOf(Color(0xFF2A004D), Color(0xFF1C2526))
                ),
                shape = RoundedCornerShape(12.dp)
            ),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = isSelected,
                onClick = { onClick() },
                colors = RadioButtonDefaults.colors(
                    selectedColor = Color(0xFFBB86FC),
                    unselectedColor = Color.White
                )
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = methodName,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White.copy(alpha = 0.9f)
            )
        }
    }
}

@Composable
fun UPIAppOption(
    appIconRes: Int,
    appName: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .size(60.dp) // Adjusted size for better spacing
            .padding(4.dp)
            .clickable { onClick() }
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                brush = Brush.linearGradient(
                    colors = if (isSelected) listOf(Color(0xFFFFD700), Color(0xFFBB86FC))
                    else listOf(Color(0xFFBB86FC), Color(0xFF6200EE))
                ),
                shape = RoundedCornerShape(8.dp)
            )
            .background(
                Brush.linearGradient(
                    colors = listOf(Color(0xFF2A004D), Color(0xFF1C2526))
                ),
                shape = RoundedCornerShape(8.dp)
            ),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = appIconRes),
                contentDescription = appName,
                modifier = Modifier
                    .size(40.dp)
                    .padding(bottom = 4.dp)
            )
            Text(
                text = appName,
                style = MaterialTheme.typography.bodySmall,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}