package com.alfacomics.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.alfacomics.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel internal constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    @Suppress("UNUSED") // Suppress until integrated with UI
    private val _userId = mutableStateOf<String?>(null)
    val userId: String? get() = _userId.value

    private val _username = mutableStateOf<String?>(null)
    val username: String? get() = _username.value

    private val _email = mutableStateOf<String?>(null)
    val email: String? get() = _email.value

    private val _token = mutableStateOf<String?>(null)
    val token: String? get() = _token.value

    init {
        checkLoginStatus()
    }

    private fun checkLoginStatus() {
        val token = authRepository.getToken()
        if (token != null) {
            _isLoggedIn.value = true
            _userId.value = authRepository.getUserId()
            _username.value = authRepository.getUsername()
            _email.value = authRepository.getEmail()
            _token.value = token
        } else {
            _isLoggedIn.value = false
            _userId.value = null
            _username.value = null
            _email.value = null
            _token.value = null
        }
    }

    fun login(usernameOrEmail: String, password: String, onResult: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            val result = authRepository.login(usernameOrEmail, password)
            if (result.isSuccess) {
                val response = result.getOrThrow()
                _isLoggedIn.value = true
                _userId.value = response.userId.toString()
                _username.value = response.username
                _email.value = usernameOrEmail
                _token.value = response.accessToken
                onResult(true, null)
            } else {
                _isLoggedIn.value = false
                _userId.value = null
                _username.value = null
                _email.value = null
                _token.value = null
                onResult(false, result.exceptionOrNull()?.message)
            }
        }
    }

    fun signup(
        username: String,
        fullName: String,
        email: String,
        password: String,
        mobileNumber: String,
        termsAccepted: Boolean,
        onResult: (Boolean, String?) -> Unit
    ) {
        viewModelScope.launch {
            val result = authRepository.signup(username, fullName, email, mobileNumber, password, termsAccepted)
            if (result.isSuccess) {
                val response = result.getOrThrow()
                _isLoggedIn.value = true
                _userId.value = response.userId.toString()
                _username.value = response.username
                _email.value = email
                _token.value = response.accessToken
                onResult(true, null)
            } else {
                _isLoggedIn.value = false
                _userId.value = null
                _username.value = null
                _email.value = null
                _token.value = null
                onResult(false, result.exceptionOrNull()?.message)
            }
        }
    }

    fun logout() {
        authRepository.logout()
        _isLoggedIn.value = false
        _userId.value = null
        _username.value = null
        _email.value = null
        _token.value = null
    }

    class Factory(private val authRepository: AuthRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
                return AuthViewModel(authRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}