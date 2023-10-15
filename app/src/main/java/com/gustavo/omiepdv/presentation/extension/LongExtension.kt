package com.gustavo.omiepdv.presentation.extension

import java.util.Calendar

fun Long.toDate(): String {
    val c = Calendar.getInstance().apply { timeInMillis = this@toDate }
    return "${c.get(Calendar.DAY_OF_MONTH)}/${c.get(Calendar.MONTH)}/${c.get(Calendar.YEAR)} - ${c.get(Calendar.HOUR_OF_DAY)}:${c.get(Calendar.MINUTE)}"
}