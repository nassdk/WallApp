package com.nassdk.wallapp.library.coreui.util

import android.content.res.Resources
import java.text.SimpleDateFormat
import java.util.*

fun convertDate(dateInMilliseconds: Long?): String {
    return try {
        val sdf = SimpleDateFormat("dd.MM.yyyy")
        val netDate = Date(dateInMilliseconds ?: 0)
        sdf.format(netDate)
    } catch (e: Exception) {
        "Недавно"
    }
}

val DP = Resources.getSystem().displayMetrics.density
