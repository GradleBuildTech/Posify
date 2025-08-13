package com.example.offline.repository.database.converter

import com.example.core.models.User

fun User.combineWith(
    other: User
): User? {
    if (this.id != other.id) {
        return null
    }
    return this
}