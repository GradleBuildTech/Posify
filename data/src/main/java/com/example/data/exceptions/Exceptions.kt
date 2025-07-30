package com.example.data.exceptions



object NoConnectivityException: RuntimeException() {
    private fun readResolve(): Any = NoConnectivityException
}


data class ApiException(
    val error: Error?,
    val httpCode: Int,
    val httpMessage: String,
) : RuntimeException()