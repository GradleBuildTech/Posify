package com.example.components.common.textField

import com.example.core.internal.machine.StateFunction

// Represents the state of a text field in a state machine.
sealed interface TextFieldState { val text: String }

data class Idle(override val text: String = "") : TextFieldState
data class Typing(override val text: String) : TextFieldState
data class Cleared(override val text: String = "") : TextFieldState

// Represents events that can occur in the text field state machine.
sealed interface TextFieldEvent
data class InputText(val value: String) : TextFieldEvent
object ClearText : TextFieldEvent

/**
 * State function for the text field state machine.
 * It defines how the state transitions based on events.
 */
val textFieldStateFunction: StateFunction<TextFieldState, TextFieldEvent> = mapOf(
    Idle::class to mapOf(
        InputText::class to  { _, event ->
            val value = (event as InputText).value
            if (value.isBlank()) Idle() else Typing(value)
        },
        ClearText::class to  { _, _ -> Cleared() }
    ),
    Typing::class to mapOf(
        InputText::class to  { _, event ->
            val value = (event as InputText).value
            if (value.isBlank()) Idle() else Typing(value)
        },
        ClearText::class to  { _, _ -> Cleared() }
    ),
    Cleared::class to mapOf(
        InputText::class to  { _, event ->
            val value = (event as InputText).value
            if (value.isBlank()) Idle() else Typing(value)
        }
    )
)
