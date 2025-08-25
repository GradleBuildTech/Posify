package com.example.core.models.enum

enum class TableStatus(val value: String) {
    ALL(""),
    EMPLOY("TIU"),
    EMPTY("ETB");

    //FromValues
    companion object {
        fun fromValue(value: String?): TableStatus? {
            return TableStatus.entries.firstOrNull { it.value.lowercase() == value?.lowercase() }
        }
    }
}

