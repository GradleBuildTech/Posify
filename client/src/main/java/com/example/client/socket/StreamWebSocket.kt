package com.example.client.socket

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import okhttp3.WebSocket
import okhttp3.WebSocketListener

const val EVENT_BUFFER_SIZE = 100
const val SOCKET_CLOSE_CODE = 1000
const val SOCKET_CLOSE_REASON = "Client closed the connection"

/** StreamWebSocket is a wrapper around an OkHttp WebSocket that provides a flow of socket events.
 * It allows sending messages and listening for incoming messages, errors, and connection events.
 *
 * @param socketCreator A function that creates a WebSocket instance with a listener.
 */
class StreamWebSocket(
    socketCreator: (WebSocketListener) -> WebSocket
) {
    private val socketEventFlow = MutableSharedFlow<StreamSocketEvent>(extraBufferCapacity = EVENT_BUFFER_SIZE)

    private val webSocket: WebSocket = socketCreator(object : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: okhttp3.Response) {
            // Handle successful connection
            socketEventFlow.tryEmit(value = StreamSocketEvent.Message("WebSocket opened successfully"))
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            // Emit incoming message
            socketEventFlow.tryEmit(value = StreamSocketEvent.Message(text))
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: okhttp3.Response?) {
            socketEventFlow.tryEmit(value = StreamSocketEvent.Error(
                ErrorMessageResponse(
                    code = -1,
                    message = t.message ?: "Unknown error",
                    statusCode = response?.code ?: -1
                )
            ))
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            if(code == SOCKET_CLOSE_CODE) {
                socketEventFlow.tryEmit(value = StreamSocketEvent.Error(
                    ErrorMessageResponse()
                ))
            }
        }
    })

    /* Socket actions */
    fun listen(): Flow<StreamSocketEvent> = socketEventFlow.asSharedFlow()
    fun close() = webSocket.close(SOCKET_CLOSE_CODE, SOCKET_CLOSE_REASON)
    fun send(message: String): Boolean = webSocket.send(message)
}

/** StreamSocketEvent represents the different types of events that can occur in a WebSocket connection.
 * It includes messages, errors, and other relevant information.
 */
sealed class StreamSocketEvent {
    data class Error(val error: ErrorMessageResponse) : StreamSocketEvent()
    data class Message(val message: String) : StreamSocketEvent()
}