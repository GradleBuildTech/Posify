package com.example.client.socket

data class ErrorDetail(
    val code: Int,
    val message: List<String>
)