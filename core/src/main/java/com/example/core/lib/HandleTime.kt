package com.example.core.lib
import android.os.Build
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

/* * This file provides a utility function to format the current date and time.
 * It requires API level 26 or higher due to the use of java.time package.
 * The formatted string is in the format "EEEE, dd MMM HH:mm" (e.g., "Monday, 01 Jan 12:00").
 */
object HandleTime{
    fun getMMMMEEEd(): String {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            throw UnsupportedOperationException("This method requires API level 26 or higher")
        }
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("EEEE, dd MMM HH:mm", Locale.ENGLISH)
        val formatted = current.format(formatter)

        return formatted
    }
}