package com.abhishek.shelfapp.utils.common

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object CalenderUtils {

    fun millisToDate(millis: Long): String {
        // Convert milliseconds to a Date object
        val date = Date(millis)

        // Create a date format with the desired pattern
        val dateFormat = SimpleDateFormat("d MMMM yyyy", Locale.getDefault())

        // Format the date as a string
        return dateFormat.format(date)
    }
}