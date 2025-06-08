package com.alfacomics.presentation.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.alfacomics.presentation.viewmodel.AuthViewModel

@Composable
fun SignUpScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel
) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var mobileNumber by remember { mutableStateOf("") }
    var termsAccepted by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var showTermsDialog by remember { mutableStateOf(false) }
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
            text = "Sign Up for Alfa Comics",
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
            modifier = Modifier.padding(bottom = 24.dp),
            textAlign = TextAlign.Center
        )

        OutlinedTextField(
            value = fullName,
            onValueChange = { fullName = it },
            label = { Text("Full Name", color = Color.White) },
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

        Spacer(modifier = Modifier.height(16.dp))

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
            value = mobileNumber,
            onValueChange = { mobileNumber = it },
            label = { Text("Mobile Number", color = Color.White) },
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

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = termsAccepted,
                onCheckedChange = { termsAccepted = it },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color(0xFFBB86FC),
                    uncheckedColor = Color.White,
                    checkmarkColor = Color.White
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "I agree to the Terms and Conditions",
                color = Color.White,
                fontSize = 14.sp,
                modifier = Modifier.clickable {
                    showTermsDialog = true
                }
            )
        }

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
                if (email.isBlank() || password.isBlank() || mobileNumber.isBlank() || fullName.isBlank()) {
                    errorMessage = "Please fill all required fields"
                    return@Button
                }
                if (!termsAccepted) {
                    errorMessage = "Please accept the Terms and Conditions"
                    return@Button
                }
                authViewModel.signup(fullName, email, password, mobileNumber, navController)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFBB86FC),
                contentColor = Color.White
            )
        ) {
            Text("Sign Up", fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(
            onClick = {
                navController.navigate("login") {
                    popUpTo("signup") { inclusive = true }
                }
            }
        ) {
            Text(
                text = "Already have an account? Login",
                color = Color.White,
                fontSize = 14.sp
            )
        }
    }

    if (showTermsDialog) {
        Dialog(onDismissRequest = { showTermsDialog = false }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF121212))
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Terms and Conditions",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "This is a dummy Terms and Conditions text. By signing up, you agree to our policies and terms of use. We respect your privacy and will not share your data without consent.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.7f),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = { showTermsDialog = false },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFBB86FC),
                            contentColor = Color.White
                        )
                    ) {
                        Text("Close")
                    }
                }
            }
        }
    }
}