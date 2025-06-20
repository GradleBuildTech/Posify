package com.example.core.extensions

import android.net.http.HttpException
import com.example.core.modes.ErrorResponse
import com.squareup.moshi.JsonDataException
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import okio.IOException
import java.io.InterruptedIOException
import java.net.UnknownHostException
import kotlin.experimental.ExperimentalTypeInference

@OptIn(ExperimentalTypeInference::class)
fun <T> flowTransform(@BuilderInference block: suspend FlowCollector<T>.() -> T) = flow {
    runCatching { block() }
        .onSuccess { result -> emit(result) }
        .onFailure { exception -> throw exception }
}

//private fun Throwable.mapError():  Throwable {
//    val NoConnectivityException = null
//    return when (this) {
//        is UnknownHostException,
//        is InterruptedIOException -> NoConnectivityException
//        is HttpException -> {
//            val errorResponse = parseErrorResponse(response())
//            ApiException(
//                errorResponse?.toModel(),
//                code(),
//                message()
//            )
//        }
//        else -> this
//    }
//}

//private fun parseErrorResponse(response: Response<*>?): ErrorResponse? {
//    val jsonString = response?.errorBody()?.string()
//    return try {
//        val moshi = MoshiBuilderProvider.moshiBuilder.build()
//        val adapter = moshi.adapter(ErrorResponse::class.java)
//        adapter.fromJson(jsonString.orEmpty())
//    } catch (_: IOException) {
//        null
//    } catch (_: JsonDataException) {
//        null
//    }
//}