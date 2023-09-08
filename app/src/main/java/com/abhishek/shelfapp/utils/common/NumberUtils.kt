package com.abhishek.shelfapp.utils.common

import java.text.DecimalFormat

fun getCommaSeparatedNumber(number: Double): String {
    val formatter = DecimalFormat("##,##,##,##,###.##")
    return formatter.format(number).toString()
}