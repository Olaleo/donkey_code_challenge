package com.example.donkey_code_challenge.model

data class ErrorModel(
    val error_code: String,
    val message: String,
    val status_code: Int
)