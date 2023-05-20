package io.github.qobiljon.stress.core.api.requests

data class SignInRequest(
    val email: String,
    val password: String,
)