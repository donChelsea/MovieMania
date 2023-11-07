package com.example.moviemania.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun convertRuntime(time: Int): String {
    val hours: Int = time / 60
    val minutes: Int = time % 60
    return String.format("%d hr %02d min", hours, minutes)
}

@RequiresApi(Build.VERSION_CODES.O)
fun convertReleasedDate(date: String): String {
    var date = LocalDate.parse(date)
    var formatter = DateTimeFormatter.ofPattern("MMMM-dd-yyyy")
    return date.format(formatter).replace("-", " ")
}