package com.alfacomics.presentation.ui.screens.store

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.alfacomics.data.repository.BuyerDetails
import java.util.regex.Pattern

@Composable
fun PurchaseForm(
    comicId: Int,
    onBuyClicked: (BuyerDetails) -> Unit // Callback to show payment options modal with buyer details
) {
    var buyerName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var mobileNumber by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var pinCode by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Focus requesters for each text field
    val buyerNameFocusRequester = remember { FocusRequester() }
    val emailFocusRequester = remember { FocusRequester() }
    val mobileNumberFocusRequester = remember { FocusRequester() }
    val addressFocusRequester = remember { FocusRequester() }
    val pinCodeFocusRequester = remember { FocusRequester() }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Header
        Text(
            text = "Buyer Details",
            style = MaterialTheme.typography.titleMedium,
            color = Color.White,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Buyer Name
        OutlinedTextField(
            value = buyerName,
            onValueChange = { buyerName = it },
            label = { Text("Buyer Name", color = Color.White) },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(buyerNameFocusRequester)
                .focusable(),
            textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.Gray
            )
        )

        // Email
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email", color = Color.White) },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(emailFocusRequester)
                .focusable(),
            textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.Gray
            )
        )

        // Mobile Number
        OutlinedTextField(
            value = mobileNumber,
            onValueChange = { mobileNumber = it },
            label = { Text("Mobile Number", color = Color.White) },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(mobileNumberFocusRequester)
                .focusable(),
            textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.Gray
            )
        )

        // Address
        OutlinedTextField(
            value = address,
            onValueChange = { address = it },
            label = { Text("Address", color = Color.White) },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(addressFocusRequester)
                .focusable(),
            textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.Gray
            )
        )

        // Pin Code
        OutlinedTextField(
            value = pinCode,
            onValueChange = { pinCode = it },
            label = { Text("Pin Code", color = Color.White) },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(pinCodeFocusRequester)
                .focusable(),
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
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Buy Button
        Button(
            onClick = {
                // Form validation
                errorMessage = validateForm(buyerName, email, mobileNumber, address, pinCode)
                if (errorMessage == null) {
                    // Save buyer details and proceed to payment options
                    val buyerDetails = BuyerDetails(
                        comicId = comicId,
                        buyerName = buyerName,
                        email = email,
                        mobileNumber = mobileNumber,
                        address = address,
                        pinCode = pinCode,
                        purchaseTimestamp = "2025-05-20 03:45 PM" // Updated to current date/time
                    )
                    onBuyClicked(buyerDetails)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(text = "Buy")
        }
    }
}

private fun validateForm(
    buyerName: String,
    email: String,
    mobileNumber: String,
    address: String,
    pinCode: String
): String? {
    // Validate Buyer Name
    if (buyerName.isBlank()) {
        return "Buyer Name is required"
    }

    // Validate Email
    val emailPattern = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
    )
    if (email.isBlank()) {
        return "Email is required"
    } else if (!emailPattern.matcher(email).matches()) {
        return "Invalid email format"
    }

    // Validate Mobile Number
    val mobilePattern = Pattern.compile("^[0-9]{10}\$")
    if (mobileNumber.isBlank()) {
        return "Mobile Number is required"
    } else if (!mobilePattern.matcher(mobileNumber).matches()) {
        return "Mobile Number must be 10 digits"
    }

    // Validate Address
    if (address.isBlank()) {
        return "Address is required"
    }

    // Validate Pin Code
    val pinCodePattern = Pattern.compile("^[0-9]{6}\$")
    if (pinCode.isBlank()) {
        return "Pin Code is required"
    } else if (!pinCodePattern.matcher(pinCode).matches()) {
        return "Pin Code must be 6 digits"
    }

    return null // No errors
}