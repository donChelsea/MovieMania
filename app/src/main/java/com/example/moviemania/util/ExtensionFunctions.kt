package com.example.moviemania.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun Int.convertRuntime(): String {
    val hours: Int = this / 60
    val minutes: Int = this % 60
    return "%d hr %02d min".format(hours, minutes)
}

@RequiresApi(Build.VERSION_CODES.O)
fun String.convertReleasedDate(): String {
    val date = LocalDate.parse(this)
    val formatter = DateTimeFormatter.ofPattern("MMMM-dd-yyyy")
    return date.format(formatter).replace("-", " ")
}