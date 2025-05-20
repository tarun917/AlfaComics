package com.alfacomics.presentation.ui.screens.store

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PurchaseForm(
    onBuyClicked: () -> Unit
) {
    var buyerName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var mobileNumber by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var pinCode by remember { mutableStateOf("") }

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
            modifier = Modifier.fillMaxWidth(),
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
            modifier = Modifier.fillMaxWidth(),
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
            modifier = Modifier.fillMaxWidth(),
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
            modifier = Modifier.fillMaxWidth(),
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
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.Gray
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Buy Button (Placeholder for future functionality)
        Button(
            onClick = { onBuyClicked() },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(text = "Buy")
        }
    }
}