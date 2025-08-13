package com.example.offline.repository.domain.user

import com.example.core.models.User


fun User.toEntity(): UserEntity {
    return UserEntity(
        id = this.id,
        fullName = this.fullName,
        phone = this.phone,
        email = this.email,
        userName = this.userName,
        isFirstLogin = this.isFirstLogin, // Assuming isFirstLogin is an enum
        tenantId = this.tenant?.id?.toString(),
        tenantName = this.tenant?.name,
    )
}