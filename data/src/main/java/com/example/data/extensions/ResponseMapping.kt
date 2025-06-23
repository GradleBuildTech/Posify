package com.example.data.extensions

import com.example.client.models.response.ErrorResponse
import com.example.client.providers.moshi.MoshiBuilderProvider
import com.example.data.adaptor.toModel
import com.example.domain.exceptions.ApiException
import com.example.domain.exceptions.NoConnectivityException
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
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

private fun Throwable.mapError(): Throwable {
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

private fun parseErrorResponse(response: Response<*>?): ErrorResponse {
    val jsonString = response?.errorBody()?.string()
    return try {
        val moshi = MoshiBuilderProvider.moshiBuilder.build()
        val adapter = moshi.adapter(ErrorResponse::class.java)
        adapter.fromJson(jsonString.orEmpty()) ?: ErrorResponse()
    } catch (_: Exception) {
        ErrorResponse()
    }
}