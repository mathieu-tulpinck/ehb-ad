package com.saxomoose.frontend.ui.auth.register

/**
 * Data validation state of the registration form.
 */
data class RegisterFormState(
    val nameError: Int? = null,
    val usernameError: Int? = null,
    val passwordError: Int? = null,
    val isDataValid: Boolean = false
)