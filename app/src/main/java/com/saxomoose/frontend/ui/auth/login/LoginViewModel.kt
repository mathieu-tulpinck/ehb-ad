package com.saxomoose.frontend.ui.auth.login

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.*
import com.saxomoose.frontend.R
import com.saxomoose.frontend.services.BackendApi
import com.saxomoose.frontend.ui.auth.LoginCredentials
import com.saxomoose.frontend.ui.auth.WrappedBody
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

private const val TAG = "LoginViewModel"

class LoginViewModelFactory : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = LoginViewModel(null, null) as T
}

class LoginViewModel(
    var userId: Int?,
    var token: String?
    ) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> = _loginResult

    fun login(username: String, password: String) {
        val rawBody = Json.encodeToJsonElement(WrappedBody(LoginCredentials(username, password, android.os.Build.MODEL)))
        val copy = rawBody.jsonObject.mapValues { it.value.jsonObject.toMutableMap() }
            .toMutableMap()
        val copyWithoutType = copy.apply {
            this["data"]?.apply {
                this.remove("type")
            }
        }
        val jsonString = Json.encodeToString(copyWithoutType)
        val body = jsonString.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

        viewModelScope.launch {
            try {
                val response = BackendApi().retrofitService.login(body)
                token = response.token
                userId = response.userId
                if (token != null && userId != null) {
                    _loginResult.value = true
                }
            } catch (e: Exception) {
                Log.e(TAG, e.message.toString())
                _loginResult.value = false
            }
        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    private fun isUserNameValid(username: String): Boolean {
        return if (username.length <= 255) {
            return true
        } else if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length >= 8
    }
}