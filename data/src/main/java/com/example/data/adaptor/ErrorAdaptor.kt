package com.example.data.adaptor

import com.example.client.models.response.ErrorResponse
import com.example.domain.models.Error


internal fun ErrorResponse.toModel(): Error = Error(
    message = this.message ?: "Unknown error",
    code = 0 // Assuming a default code, adjust as necessary
)