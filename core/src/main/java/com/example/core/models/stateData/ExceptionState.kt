package com.example.core.models.stateData

import okhttp3.internal.http2.ErrorCode



data class ExceptionState(val errorCode: ErrorCode = ErrorCode.INTERNAL_ERROR, val errorMessage: String) {
    companion object {
        fun unknownError() = ExceptionState(
            errorCode = ErrorCode.INTERNAL_ERROR,
            errorMessage = "Unknown error occurred. Please try again later."
        )

        fun notMatchTypeValue() = ExceptionState(
            errorCode = ErrorCode.INTERNAL_ERROR,
            errorMessage = "Not match type value."
        )
    }
}