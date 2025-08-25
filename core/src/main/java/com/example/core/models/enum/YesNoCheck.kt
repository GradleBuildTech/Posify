package com.example.core.models.enum


enum class YesNoCheck(val value: String) {
    ALL("A"),
    YES("Y"),
    NO("N");

    //FromValues
    companion object {
        fun fromValue(value: String?): YesNoCheck? {
            return YesNoCheck.entries.firstOrNull { it.value.lowercase() == value?.lowercase() }
        }
    }
}