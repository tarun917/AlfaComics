package com.alfacomics.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.alfacomics.data.repository.AuthRepository
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    var isLoggedIn by mutableStateOf(false)
    var token: String? by mutableStateOf(null)
    var refreshToken: String? by mutableStateOf(null)
    var userId: Long? by mutableStateOf(null)
    var username: String? by mutableStateOf(null)
    var errorMessage by mutableStateOf<String?>(null)

    fun login(email: String, password: String, navController: NavHostController) {
        viewModelScope.launch {
            val result = AuthRepository.login(email, password)
            result.onSuccess {
                isLoggedIn = true
                token = it.token
                refreshToken = it.refresh_token
                userId = it.userId
                username = it.username
                navController.navigate("home") {
                    popUpTo("login") { inclusive = true }
                }
            }.onFailure {
                errorMessage = it.message
            }
        }
    }

    fun signup(fullName: String, email: String, password: String, mobileNumber: String, navController: NavHostController) {
        viewModelScope.launch {
            val result = AuthRepository.signup(fullName, email, password, mobileNumber)
            result.onSuccess {
                isLoggedIn = true
                token = it.token
                refreshToken = it.refresh_token
                userId = it.userId
                username = it.username
                navController.navigate("login") {
                    popUpTo("signup") { inclusive = true }
                }
            }.onFailure {
                errorMessage = it.message
            }
        }
    }
}