package com.alfacomics.presentation.ui.screens.profile

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.alfacomics.data.repository.DummyData

data class CoinPackage(val coins: Int, val price: Int)

@Composable
fun CoinPurchaseScreen(
    navController: NavHostController
) {
    val context = LocalContext.current
    val coinPackages = listOf(
        CoinPackage(coins = 100, price = 99),
        CoinPackage(coins = 500, price = 399),
        CoinPackage(coins = 1000, price = 799)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top Bar with Back Button and Title
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color(0xFFBB86FC)
                )
            }
            Text(
                text = "Buy Alfa Coins",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.size(32.dp)) // Placeholder for symmetry
        }

        // Current Balance
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Current Balance",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White
                )
                Text(
                    text = "${DummyData.getUserProfile().alfaCoins} Coins",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xFFBB86FC),
                    fontSize = 20.sp
                )
            }
        }

        // Coin Packages
        Text(
            text = "Choose a Package",
            style = MaterialTheme.typography.titleMedium,
            color = Color.White,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(coinPackages) { coinPackage ->
                CoinPackageItem(
                    coinPackage = coinPackage,
                    onClick = {
                        // Simulate coin purchase
                        DummyData.addAlfaCoins(coinPackage.coins)
                        Toast.makeText(context, "Purchased ${coinPackage.coins} Alfa Coins!", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}

@Composable
fun CoinPackageItem(
    coinPackage: CoinPackage,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${coinPackage.coins} Coins",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )
            Text(
                text = "₹${coinPackage.price}",
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFFBB86FC),
                fontSize = 20.sp
            )
        }
    }
}