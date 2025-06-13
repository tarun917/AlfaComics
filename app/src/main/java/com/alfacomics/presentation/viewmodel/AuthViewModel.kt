package com.alfacomics.presentation.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.alfacomics.data.repository.AuthRepository
import com.alfacomics.data.repository.DummyData
import com.alfacomics.data.repository.UserProfile
import kotlinx.coroutines.launch

class AuthViewModel(context: Context) : ViewModel() {
    private val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    var isLoggedIn by mutableStateOf(loadLoginState())
    var token: String? by mutableStateOf(loadToken())
    var refreshToken: String? by mutableStateOf(loadRefreshToken())
    var userId: Long? by mutableStateOf(loadUserId())
    var username: String? by mutableStateOf(loadUsername())
    var email: String? by mutableStateOf(loadEmail())
    var errorMessage by mutableStateOf<String?>(null)

    private fun saveLoginState(isLoggedIn: Boolean) {
        prefs.edit().putBoolean("is_logged_in", isLoggedIn).apply()
        this.isLoggedIn = isLoggedIn
        DummyData.isLoggedIn = isLoggedIn
    }

    private fun saveAuthData(
        token: String?,
        refreshToken: String?,
        userId: Long?,
        username: String?,
        email: String?
    ) {
        prefs.edit().apply {
            putString("token", token)
            putString("refresh_token", refreshToken)
            putLong("user_id", userId ?: -1)
            putString("username", username)
            putString("email", email)
            apply()
        }
        this.token = token
        this.refreshToken = refreshToken
        this.userId = userId
        this.username = username
        this.email = email

        // Update DummyData.userProfile
        if (userId != null && username != null && email != null) {
            DummyData.userProfile = UserProfile(
                userId = userId,
                username = username,
                email = email,
                profilePictureResourceId = com.alfacomics.R.drawable.ic_launcher_background,
                profilePictureBitmap = null,
                aboutMe = "",
                alfaCoins = 500,
                followers = emptyList(),
                following = listOf("SuperheroLover", "FantasyReader", "ActionHero")
            )
            DummyData.allUserProfiles[username] = DummyData.userProfile
        }
    }

    private fun loadLoginState(): Boolean = prefs.getBoolean("is_logged_in", false)

    private fun loadToken(): String? = prefs.getString("token", null)

    private fun loadRefreshToken(): String? = prefs.getString("refresh_token", null)

    private fun loadUserId(): Long? {
        val userId = prefs.getLong("user_id", -1)
        return if (userId != -1L) userId else null
    }

    private fun loadUsername(): String? = prefs.getString("username", null)

    private fun loadEmail(): String? = prefs.getString("email", null)

    fun login(email: String, password: String, navController: NavHostController) {
        viewModelScope.launch {
            val result = AuthRepository.login(email, password)
            result.onSuccess {
                saveLoginState(true)
                saveAuthData(it.token, it.refreshToken, it.userId.toLong(), it.username, email)
                navController.navigate("home") {
                    popUpTo("login") { inclusive = true }
                }
            }.onFailure {
                errorMessage = it.message
            }
        }
    }

    fun signup(
        fullName: String,
        email: String,
        password: String,
        mobileNumber: String,
        termsAccepted: Boolean,
        navController: NavHostController
    ) {
        viewModelScope.launch {
            val result = AuthRepository.signup(fullName, email, password, mobileNumber, termsAccepted)
            result.onSuccess {
                saveLoginState(true)
                saveAuthData(it.token, it.refreshToken, it.userId.toLong(), it.username, email)
                navController.navigate("home") {
                    popUpTo("signup") { inclusive = true }
                }
            }.onFailure {
                errorMessage = it.message ?: "Signup failed"
            }
        }
    }

    fun logout(navController: NavHostController) {
        saveLoginState(false)
        saveAuthData(null, null, null, null, null)
        DummyData.clearUserData()
        navController.navigate("login") {
            popUpTo("home") { inclusive = true }
        }
    }
}