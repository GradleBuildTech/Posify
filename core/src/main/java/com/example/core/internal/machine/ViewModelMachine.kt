package com.example.core.internal.machine


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.models.stateData.Either
import com.example.core.models.stateData.ExceptionState
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * A base ViewModel class that manages UI state and events.
 * It provides a structured way to handle events and update the UI state.
 * This class is designed to be extended by other ViewModels
 * that need to manage both state and events.
 */
abstract class ViewModelMachine<UiState, Event>(initialState: UiState) : ViewModel() {
    private val events = MutableSharedFlow<Event>(replay = 0)
    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(initialState)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            events.collect { event -> handleEvent(event) }
        }
    }

    protected abstract suspend fun handleEvent(event: Event)

    protected fun setUiState(update: UiState.() -> UiState) {
        _uiState.update { _uiState.value.update() }
    }

    fun onEvent(event: Event) {
        viewModelScope.launch { events.emit(event) }
    }

    ///✨===============================================
    /**
     * Loads initial data asynchronously and updates the UI state based on the results.
     * @param initialLoadState A lambda function that defines the initial state of the UI.
     * @param errorLoadState A lambda function that defines the error state of the UI when an exception occurs.
     * @param listRequest A list of flows that represent the requests to be made.
     * @param listResponse A lambda function that processes the response of each request.
     * This function will set the initial state of the UI and handle errors by updating the UI state accordingly.
     * ```kotlin
     * loadInitialData(
     *    initialLoadState = { this.copy(isLoading = true) },
     *    errorLoadState = { errorMessage ->
     *    this.copy(isLoading = false, error = errorMessage) },
     *    listRequest = listOf(
     *    flowOf(Either.Right("Data 1")),
     *    flowOf(Either.Right("Data 2")),
     *    flowOf(Either.Left(ExceptionState("Error occurred")))
     *    ),
     */
    fun loadInitialData(
        initialLoadState: UiState.() -> UiState,
        errorLoadState: UiState.(String) -> UiState,
        listRequest: List<Flow<Either<ExceptionState, Any>>>,

        listResponse: (Either<ExceptionState, Any>, Int) -> Unit
    ) {
        setUiState { initialLoadState() }
        val request = listRequest.map { req ->
            viewModelScope.async {
                req.catch { e ->
                    emit(Either.Left(ExceptionState(errorMessage = e.message ?: "")))
                }
            }
        }

        viewModelScope.launch {
            try {
                ///✨===============================================
                ///[awaitAll] is a suspend function that waits for all the given deferred values to complete.
                val results = awaitAll(*request.toTypedArray())
//                results.forEachIndexed { index, rp -> launch { rp.collect { listResponse(it, index) } }  }
                results.forEachIndexed { index, rp -> rp.collect { listResponse(it, index) } }
            } catch (e: Exception) {
                Log.e("StateAndEventViewModel", "Error loading initial data", e)
                setUiState { _uiState.value.errorLoadState(e.message ?: "") }
            }
        }
    }

    ///✨===============================================
    ///[retry] is a suspend function that takes in a block and retries the block if it fails.
    private suspend fun <T> retry(
        times: Int,
        initialDelayMillis: Long = 100,
        maxDelayMillis: Long = 1000,
        factor: Double = 2.0,
        block: suspend () -> T
    ): T {
        var currentDelay = initialDelayMillis
        repeat(times) {
            try {
                return block()
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
            delay(currentDelay)
            currentDelay = (currentDelay * factor).toLong().coerceAtMost(maxDelayMillis)
        }
        return block() // last attempt
    }
}