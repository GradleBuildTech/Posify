package com.example.domain.exceptions

import com.example.domain.models.Error


object NoConnectivityException: RuntimeException() {
    private fun readResolve(): Any = NoConnectivityException
}


data class ApiException(
    val error: Error?,
    val httpCode: Int,
    val httpMessage: String,
) : RuntimeException()