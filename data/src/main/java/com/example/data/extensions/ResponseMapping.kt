package com.example.data.extensions

import com.example.client.providers.moshi.MoshiBuilderProvider
import com.example.core.models.response.ErrorResponse
import com.example.core.models.response.ResponseData
import com.example.core.models.stateData.Either
import com.example.core.models.stateData.Either.Left
import com.example.core.models.stateData.Either.Right
import com.example.core.models.stateData.ExceptionState
import com.example.data.adaptor.toModel
import com.example.domain.exceptions.ApiException
import com.example.domain.exceptions.NoConnectivityException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import okhttp3.internal.http2.ErrorCode
import retrofit2.HttpException
import retrofit2.Response
import java.net.UnknownHostException
import kotlin.experimental.ExperimentalTypeInference

/**
 * Extension function to create a Flow that emits a single value
 * and handles exceptions by mapping them to specific error types.
 * @param block The suspend function to execute within the Flow context.
 * @return A Flow that emits the result of the block or throws a mapped exception.
 * Usage:
 * ```kotlin
 * override fun getModels(): Flow<List<Model>> = flowTransform {
 *         apiService.getResponses().toModels()
 * }
 *```
 */
@OptIn(ExperimentalTypeInference::class)
fun<T> flowTransform(@BuilderInference block: suspend FlowCollector<T>.() -> Unit) = flow {
    runCatching { block() }
        .onFailure { throw it.mapError() }
        .onSuccess {result -> emit(result) }
}

inline fun <T> runCatchingApiCall(
    crossinline call: suspend () -> Response<T>
): Flow<T> = flow {
    runCatching { call() }
        .onFailure { throw it.mapError() }
        .onSuccess { response ->
            if (response.isSuccessful) {
                response.body()?.let { emit(it) }
                    ?: throw Exception("Empty body from successful response")
            } else {
                throw Exception("API error: ${response.errorBody()?.string()}")
            }
        }
}


fun Throwable.mapError(): Throwable {
    return when(this) {
        is UnknownHostException,
        is InterruptedException -> NoConnectivityException
        is HttpException -> {
            val errorResponse = parseErrorResponse(this.response())
            ApiException(
                error = errorResponse.toModel(),
                httpCode = this.code(),
                httpMessage = this.message()
            )
        }
        else -> this
    }
}


fun <R> ResponseData<R>.mapAndConverterToStateData(): Either<ExceptionState, R> {
    return if( (this.status == 200 || this.status == 201) && this.data != null) {
        Right<R>(this.data!!)
    } else {
        Left(
            ExceptionState(
                errorCode = ErrorCode.INTERNAL_ERROR,
                errorMessage = this.message
            )
        )
    }
}

fun parseErrorResponse(response: Response<*>?): ErrorResponse {
    val jsonString = response?.errorBody()?.string()
    return try {
        val moshi = MoshiBuilderProvider.moshiBuilder.build()
        val adapter = moshi.adapter(ErrorResponse::class.java)
        adapter.fromJson(jsonString.orEmpty()) ?: ErrorResponse()
    } catch (_: Exception) {
        ErrorResponse()
    }
}