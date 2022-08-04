package com.saxomoose.frontend.ui.auth.register

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saxomoose.frontend.R
import com.saxomoose.frontend.services.BackendApi
import com.saxomoose.frontend.ui.auth.RegisterCredentials
import com.saxomoose.frontend.ui.auth.WrappedBody
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

private const val TAG = "RegisterViewModel"

class RegisterViewModel : ViewModel() {
    private val _registerForm = MutableLiveData<RegisterFormState>()
    val registerFormState: LiveData<RegisterFormState> = _registerForm

    private val _registerResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> = _registerResult

    fun register(name: String, username: String, password: String) {
        val rawBody = Json.encodeToJsonElement(WrappedBody(RegisterCredentials(name, username, password)))
        val copy = rawBody.jsonObject.mapValues { it.value.jsonObject.toMutableMap() }.toMutableMap()
        val copyWithoutType = copy.apply {
            this["data"]?.apply {
                this.remove("type")
            }
        }
        val jsonString = Json.encodeToString(copyWithoutType)
        val body = jsonString.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

        viewModelScope.launch {
            try {
                val responseStatusCode = BackendApi().retrofitService.register(body).code()
                if (responseStatusCode == 204) {
                    _registerResult.value = true
                }
            } catch (e: Exception) {
                Log.e(TAG, e.message.toString())
                _registerResult.value = false
            }
        }
    }

    fun registerDataChanged(name: String, username: String, password: String) {
        if (!isNameValid(name)) {
            _registerForm.value = RegisterFormState(nameError = R.string.invalid_name)
        }
        else if (!isUserNameValid(username)) {
            _registerForm.value = RegisterFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _registerForm.value = RegisterFormState(passwordError = R.string.invalid_password)
        } else {
            _registerForm.value = RegisterFormState(isDataValid = true)
        }
    }

    private fun isNameValid(name: String): Boolean {
        return name.length <= 255
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