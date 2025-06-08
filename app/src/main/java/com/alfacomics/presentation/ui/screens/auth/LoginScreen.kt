package com.alfacomics.presentation.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.alfacomics.presentation.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel // Updated parameter
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    LaunchedEffect(authViewModel.errorMessage) { errorMessage = authViewModel.errorMessage }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Login to Alfa Comics",
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
            modifier = Modifier.padding(bottom = 24.dp),
            textAlign = TextAlign.Center
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email", color = Color.White) },
            modifier = Modifier.fillMaxWidth(),
            textStyle = LocalTextStyle.current.copy(color = Color.White),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color(0xFFBB86FC),
                unfocusedIndicatorColor = Color.White.copy(alpha = 0.5f)
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password", color = Color.White) },
            modifier = Modifier.fillMaxWidth(),
            textStyle = LocalTextStyle.current.copy(color = Color.White),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color(0xFFBB86FC),
                unfocusedIndicatorColor = Color.White.copy(alpha = 0.5f)
            )
        )

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

        Button(
            onClick = {
                if (email.isBlank() || password.isBlank()) {
                    errorMessage = "Please enter email and password"
                    return@Button
                }
                authViewModel.login(email, password, navController)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFBB86FC),
                contentColor = Color.White
            )
        ) {
            Text("Login", fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(
            onClick = {
                navController.navigate("signup")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Don't have an account? Sign Up",
                color = Color(0xFFBB86FC),
                fontSize = 14.sp
            )
        }
    }
}