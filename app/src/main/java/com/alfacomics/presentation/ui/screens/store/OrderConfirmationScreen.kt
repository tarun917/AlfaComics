package com.alfacomics.presentation.ui.screens.store

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun OrderConfirmationScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Purchase Successful!",
            style = MaterialTheme.typography.titleLarge,
            color = Color.Green
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Your order has been placed. You'll receive your hard copy soon!",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                navController.popBackStack("store", inclusive = false)
            }
        ) {
            Text(text = "Back to Store")
        }
    }
}