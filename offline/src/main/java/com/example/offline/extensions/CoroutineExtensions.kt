package com.example.offline.extensions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun CoroutineScope.launchCancellable(
    block: suspend CoroutineScope.() -> Unit
) {
    this.launch {
        try {
            block()
        } catch (e: Exception) {
            // Handle cancellation or other exceptions if needed
            throw e
        }
    }
}