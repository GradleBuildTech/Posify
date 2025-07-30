package com.example.data.adaptor

import com.example.core.models.response.ErrorResponse
import com.example.data.exceptions.Error


internal fun ErrorResponse.toModel(): Error = Error(
    message = this.message ?: "Unknown error",
    code = 0 // Assuming a default code, adjust as necessary
)