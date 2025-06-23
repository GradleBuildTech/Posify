package com.example.core.internal.machine

import com.example.core.internal.machine.builder.StateHandlerBuilder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.reflect.KClass

typealias StateFunction<S, E> = Map<KClass<out S>, Map<KClass<out E>, StateHandlerBuilder<S, E>>>

/**
 * StateMachine is a generic state machine implementation that allows you to define states and events.
 * It uses a state function to determine the next state based on the current state and the event sent.
 *
 * @param S The type of the state.
 * @param E The type of the event.
 * @property initialState The initial state of the state machine.
 * @property stateFunction A map that defines how states transition based on events.
 * @property defaultEventHandler A default handler for events that do not have a specific handler defined.
 * 🧠 Example:
 * ```kotlin
 * data class StateA(val data: String) : State
 * data class StateB(val data: String) : State
 * data class EventA(val info: String) : Event
 * data class EventB(val info: String) : Event
 * val stateFunction: StateFunction<State, Event> = mapOf(
 *    StateA::class to mapOf(
 *    EventA::class to { state, event -> StateB((state as StateA).data + (event as EventA).info) },
 *    EventB::class to { state, event -> StateA((state as StateA).data + (event as EventB).info) }
 *    ),
 *    StateB::class to mapOf(
 *    EventA::class to { state, event -> StateA((state as StateB).data + (event as EventA).info) },
 *    EventB::class to { state, event -> StateB((state as StateB).data + (event as EventB).info) }
 *    )
 * )
 *    val stateMachine = StateMachine(StateA("Initial"), stateFunction) { state, event ->
 *    // Default handler that returns the current state
 *    state
 *    }
 *    // Usage
 *    stateMachine.sendEvent(EventA("Hello"))
 *    stateMachine.sendEvent(EventB(" World"))
 *    println(stateMachine.state) // Output: StateA(data="Hello World")
 * ```

 */
class StateMachine<S: Any, E: Any>(
    initialState: S,
    private val stateFunction: StateFunction<S, E>,
    private val defaultEventHandler: (S, E) -> S,
) {
    private val mutex = Mutex()
    private val _state: MutableStateFlow<S> = MutableStateFlow(initialState)


    val stateFlow: StateFlow<S> = _state

    val state: S
        get() = _state.value

    /**
     * Sends an event to the state machine, which will process the event and transition to the next state.
     *
     * @param event The event to be processed.
     */
    suspend fun sendEvent(event: E) {
        mutex.withLock {
            val currentState = _state.value
            val handler = stateFunction[currentState::class]?.get(event::class)
                ?: defaultEventHandler
            _state.value = handler(currentState, event)
        }
    }

    fun stay(): S = state
}

