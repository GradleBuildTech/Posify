package com.example.domain.usecase.auth

import com.example.core.models.User
import javax.inject.Inject
import com.example.offline.repository.domain.user.DatabaseUserRepository

class AuthSaveUser @Inject constructor(
    private val databaseUserRepository: DatabaseUserRepository
) {
    /**
     * Saves the user information to the database.
     *
     * @param user The user information to save.
     */
    operator fun invoke(user: User) {
        databaseUserRepository.insertUser(user)
    }
}