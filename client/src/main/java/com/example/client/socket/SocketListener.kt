package com.example.client.socket


/** This class is designed to be extended by other classes that need to handle
 * socket events. It provides default implementations for the methods that can
 * be overridden by subclasses.
 */
open class SocketListener {
    /* * This class is designed to be extended by other classes that need to handle
     * socket events. It provides default implementations for the methods that can
     * be overridden by subclasses.
     */
    open fun onOpen() {
        // Default implementation does nothing
    }

    /* * This method is called when the socket connection is closed.
     * It can be overridden to provide custom behavior.
     */
    open fun onConnecting() {
        // Default implementation does nothing
    }

    /** This method is called when the socket connection is closed.
     * It can be overridden to provide custom behavior.
     */
    open fun onConnected() {
        // Default implementation does nothing
    }

    /** This method is called when the socket connection is closed.
     * It can be overridden to provide custom behavior.
     */
    open fun onDisconnected() {
        // Default implementation does nothing
    }


    /** This method is called when an error occurs during socket communication.
     * It can be overridden to provide custom error handling.
     */
    open fun onError(error: ErrorMessageResponse) {
        // Default implementation does nothing
    }

    /** This method is called when a message is received from the socket.
     * It can be overridden to handle incoming messages.
     */
    open fun onMessage(message: String) {
        // Default implementation does nothing
    }

}