package com.saxomoose.frontend.ui.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class Credentials
{
    abstract val userName: String
    abstract val password: String
}

@Serializable
data class RegisterCredentials(
    val name: String,
    @SerialName("email")
    override val userName: String,
    override val password: String,
    ) : Credentials()

@Serializable
data class LoginCredentials(
    @SerialName("email")
    override val userName: String,
    override val password: String,
    @SerialName("device_name")
    val deviceName: String
    ) : Credentials()

@Serializable
data class WrappedBody (
    val data: Credentials
    )
