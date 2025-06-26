package com.example.core.models

// This file defines a User data class that represents a user in the system.
data class User(
    val id: String,
    val name: String,
    val email: String,
    val phoneNumber: String? = null,
    val address: String? = null,
    val profilePictureUrl: String? = null,
    val isActive: Boolean = true
) {
    override fun toString(): String {
        return "User(id='$id', name='$name', email='$email', phoneNumber=$phoneNumber, address=$address, profilePictureUrl=$profilePictureUrl, isActive=$isActive)"
    }
}