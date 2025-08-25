package com.example.core.utils

import android.annotation.SuppressLint
import android.os.Build
import java.text.SimpleDateFormat
import java.util.Calendar

class DateUtils {
    companion object {
        const val DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"
        const val DATE_FORMAT = "yyyy-MM-dd"
        const val TIME_FORMAT = "HH:mm:ss"
    }

    // Add date utility functions here if needed
    //Get current date
    @SuppressLint("SimpleDateFormat")
    fun getCurrentDate(): String {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
            val current = Calendar.getInstance().time
            val formatter = SimpleDateFormat(DATE_FORMAT)
            return formatter.format(current)
        }
        return ""
    }

    @SuppressLint("SimpleDateFormat")
    fun getCurrentDateTime(): String {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
            val current = Calendar.getInstance().time
            val formatter = SimpleDateFormat(DATE_TIME_FORMAT)
            return formatter.format(current)
        }
        return ""
    }

    @SuppressLint("SimpleDateFormat")
    fun getCurrentTime(): String {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
            val current = Calendar.getInstance().time
            val formatter = SimpleDateFormat(TIME_FORMAT)
            return formatter.format(current)
        }
        return ""
    }
}