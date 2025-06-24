package com.example.client.socket

import okhttp3.OkHttpClient
import okhttp3.Request

/** * Factory class to create WebSocket connections.
 * It builds a request with the necessary headers and provides a method to create a WebSocket instance.
 * * Usage:
 * * 1. Create an instance of `SocketFactory`.
 * * 2. Call `createSocket` with the appropriate `ConnectionArg` to get a `StreamWebSocket`.
 * * Example:
 * * ```kotlin
 * val socketFactory = SocketFactory()
 * val connectionArg = SocketFactory.ConnectionArg.AnnonyMousConnection(
 *    endPoint = "wss://example.com/socket
 *    api",
 *    apiKey = "your_api
 * )
 * val webSocket = socketFactory.createSocket(connectionArg)
 * * webSocket.listen().collect { event ->
 * *    when (event) {
 * *        is StreamSocketEvent.Message -> println("Received message: ${event.message}")
 *          is StreamSocketEvent.Error -> println("Error: ${event.error.message}")
 *          }
 *      }
 * * 3. Use `webSocket.send("your message")` to send messages.
 */
class SocketFactory(
    private val okHttpClient: OkHttpClient = OkHttpClient()
) {

    /** Header for the WebSocket request to identify the client.
     * This header is used to pass the API key or other identification information.
     */
    companion object {
        private const val X_STREAM_CLIENT = "X-Stream-Client"
    }

    /** Builds a WebSocket request with the provided connection arguments.
     * It sets the endpoint and API key in the request headers.
     *
     * @param connectionArg The connection arguments containing the endpoint and API key.
     * @return A Request object ready to be used for creating a WebSocket connection.
     */
    private fun buildRequest(connectionArg: ConnectionArg): Request {
        return Request.Builder()
            .url(connectionArg.endPoint)
            .addHeader(X_STREAM_CLIENT, connectionArg.apiKey)
            .build()
    }

    /** Creates a WebSocket instance using the provided connection arguments.
     * It builds a request and uses the OkHttp client to create the WebSocket.
     *
     * @param connectionArg The connection arguments containing the endpoint and API key.
     * @return A StreamWebSocket instance that can be used to listen for events and send messages.
     */
    fun createSocket(connectionArg: ConnectionArg): StreamWebSocket {
        val request = buildRequest(connectionArg)
        return StreamWebSocket {okHttpClient.newWebSocket(request, it)}
    }

    /** ConnectionArg is a sealed class representing the arguments required to establish a WebSocket connection.
     * It includes both anonymous and authenticated connection types, each with its own endpoint and API key.
     */
    sealed class ConnectionArg {
        var isReconnection: Boolean = false
            private set
        abstract val endPoint: String
        abstract val apiKey: String

        data class AnnonyMousConnection(
            override val endPoint: String,
            override val apiKey: String
        ) : ConnectionArg()

        data class AuthenticatedConnection(
            override val endPoint: String,
            override val apiKey: String,
        ): ConnectionArg()
    }
}