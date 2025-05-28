package com.alfacomics.presentation.ui.screens.profile

import android.widget.Toast
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

data class FAQ(val question: String, val answer: String)

@Composable
fun SupportScreen(
    navController: NavHostController
) {
    val context = LocalContext.current
    val faqs = listOf(
        FAQ("How do I purchase Alfa Coins?", "You can purchase Alfa Coins from the Profile tab by selecting 'Buy Alfa Coins'."),
        FAQ("What are Alfa Coins used for?", "Alfa Coins are used to purchase comics and access premium features in the app."),
        FAQ("How can I upload my own comic?", "Go to the Profile tab and select 'Upload Your Comic' to submit your comic for review.")
    )

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    // Gradient background for the screen
    val gradientBackground = Brush.verticalGradient(
        colors = listOf(Color(0xFF1A0033), Color(0xFF121212)),
        startY = 0f,
        endY = 1000f
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBackground)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Back Button and Title
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { navController.popBackStack() }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Support",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFFBB86FC), Color(0xFFFFD700))
                        )
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }

        // FAQs Section
        item {
            Text(
                text = "Frequently Asked Questions",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFFBB86FC), Color(0xFFFFD700))
                    )
                ),
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                textAlign = TextAlign.Start
            )

            faqs.forEachIndexed { index, faq ->
                FAQItem(
                    faq = faq,
                    index = index
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
        }

        // Contact Form Section
        item {
            Text(
                text = "Contact Us",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFFBB86FC), Color(0xFFFFD700))
                    )
                ),
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .border(
                        width = 2.dp,
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFFBB86FC), Color(0xFF6200EE))
                        ),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .shadow(8.dp, shape = RoundedCornerShape(16.dp))
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
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Name", color = Color.White) },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = LocalTextStyle.current.copy(color = Color.White),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color(0xFFBB86FC),
                            unfocusedIndicatorColor = Color.White.copy(alpha = 0.5f)
                        )
                    )

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email", color = Color.White) },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = LocalTextStyle.current.copy(color = Color.White),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color(0xFFBB86FC),
                            unfocusedIndicatorColor = Color.White.copy(alpha = 0.5f)
                        )
                    )

                    OutlinedTextField(
                        value = message,
                        onValueChange = { message = it },
                        label = { Text("Message", color = Color.White) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp), // Reduced from 120.dp to fit better
                        textStyle = LocalTextStyle.current.copy(color = Color.White),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color(0xFFBB86FC),
                            unfocusedIndicatorColor = Color.White.copy(alpha = 0.5f)
                        )
                    )

                    // Scale animation state for the button on click
                    val buttonScale = remember { Animatable(1f) }
                    var isClicked by remember { mutableStateOf(false) }

                    // Launch the animation when the button is clicked
                    LaunchedEffect(isClicked) {
                        if (isClicked) {
                            buttonScale.animateTo(
                                targetValue = 0.95f,
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioMediumBouncy,
                                    stiffness = Spring.StiffnessLow
                                )
                            )
                            buttonScale.animateTo(
                                targetValue = 1f,
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioMediumBouncy,
                                    stiffness = Spring.StiffnessLow
                                )
                            )
                            // Simulate sending the message
                            if (name.isBlank() || email.isBlank() || message.isBlank()) {
                                Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(context, "Message sent successfully!", Toast.LENGTH_SHORT).show()
                                name = ""
                                email = ""
                                message = ""
                                navController.popBackStack()
                            }
                            isClicked = false
                        }
                    }

                    Button(
                        onClick = {
                            isClicked = true
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .scale(buttonScale.value)
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(Color(0xFFBB86FC), Color(0xFFFFD700))
                                ),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .border(
                                width = 1.dp,
                                brush = Brush.linearGradient(
                                    colors = listOf(Color(0xFFFFD700), Color(0xFFBB86FC))
                                ),
                                shape = RoundedCornerShape(12.dp)
                            )
                    ) {
                        Text(
                            text = "Send Message",
                            style = MaterialTheme.typography.titleMedium.copy(fontSize = 14.sp),
                            modifier = Modifier.padding(vertical = 6.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun FAQItem(
    faq: FAQ,
    index: Int
) {
    var expanded by remember { mutableStateOf(false) }

    // Scale animation for FAQ items
    val animatedScale by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(
            durationMillis = 800,
            delayMillis = index * 200,
            easing = FastOutSlowInEasing
        )
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .scale(animatedScale)
            .clickable { expanded = !expanded }
            .border(
                width = 2.dp,
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFFBB86FC), Color(0xFF6200EE))
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .shadow(8.dp, shape = RoundedCornerShape(16.dp))
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
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = faq.question,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = if (expanded) "Collapse" else "Expand",
                    tint = Color(0xFFBB86FC),
                    modifier = Modifier.size(24.dp)
                )
            }
            if (expanded) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = faq.answer,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.7f)
                )
            }
        }
    }
}