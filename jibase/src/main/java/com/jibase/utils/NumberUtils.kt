package com.jibase.utils

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.*
import kotlin.math.abs
import kotlin.math.ln
import kotlin.math.pow

/**
 * Created by Ngoc on 4/5/2018.
 */

fun String.toDoubleNotNull(default: Double = 0.0): Double {
    try {
        return toDoubleOrNull() ?: default
    } catch (e: Exception) {
    }
    return default
}

fun String.toIntNotNull(default: Int = 0): Int {
    try {
        return toIntOrNull() ?: default
    } catch (e: Exception) {
    }
    return default
}

fun String.toLongNotNull(default: Long = 0L): Long {
    return if (this.equals("null", false) || !this.matches(Regex("\\d*"))) default
    else this.toLong()
}

//---------------------------//
val df2Digit = DecimalFormat("0.##", DecimalFormatSymbols.getInstance(Locale.ENGLISH)).apply {
    roundingMode = RoundingMode.HALF_UP
}
val df3Digit = DecimalFormat("0.###", DecimalFormatSymbols.getInstance(Locale.ENGLISH)).apply {
    roundingMode = RoundingMode.HALF_UP
}
val dfCustomDigit = DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH)).apply {
    roundingMode = RoundingMode.HALF_UP
}

fun Double.toFormat(shortMode: Boolean = false, withCommas: Boolean = false): String {
    val result = when {
        isNaN() -> NO_DATA_TEXT
        equals(0.0) -> ZERO_VALUE_TEXT
        abs(this) in 0 until 1 -> formatToNonZeroDecimal(if (this < 0.1) 2 else 3)
        abs(this) in 1 until 100 -> df3Digit.format(this)
        else -> df2Digit.format(this)
    }
    return if (result == NO_DATA_TEXT || result == ZERO_VALUE_TEXT) result
    else {
        when {
            shortMode -> formatWithShortSuffix(result, withCommas)
            withCommas -> formatWithCommas(result)
            else -> result
        }
    }
}

private fun formatWithShortSuffix(value: String, withCommas: Boolean): String {
    val num = value.replace(",", "").trim().toDoubleOrNull()

    val suffixStart = 1000000
    val suffixList = listOf('K', 'M', 'B', 'T', 'P', 'E')

    return if (num != null) {
        if (num < suffixStart) if (withCommas) formatWithCommas(value) else value
        else {
            val exp = (ln(num) / ln(1000.toDouble())).toInt()
            val expIndex = suffixList.size.coerceAtMost(exp) - 1
            val numberValue = num / 1000.toDouble().pow((expIndex + 1).toDouble())
            val roundDigit = if (numberValue < 100) 2 else 3
            var pattern = "0."
            for (i in 0 until roundDigit) pattern += "#"
            dfCustomDigit.applyPattern(pattern)
            val number = dfCustomDigit.format(numberValue).replace(",", ".")
            val numberFormatString = if (withCommas) formatWithCommas(number) else number
            String.format("%s%c", numberFormatString, suffixList[expIndex])
        }
    } else {
        NO_DATA_TEXT
    }
}

private fun Double.formatToNonZeroDecimal(places: Int): String {
    if (this < 1) {
        val valueString = BigDecimal(this.toString()).toPlainString()
        val indexDot = valueString.indexOf(".")
        if (indexDot < valueString.length) {
            val decimalPart = valueString.substring(indexDot + 1)
            var decimalPattern = ""
            for (element in decimalPart) {
                if (element == '0') decimalPattern += "#"
                else {
                    for (j in 1..places) decimalPattern += "#"
                    break
                }
            }

            val pattern = "0" + if (decimalPattern.isEmpty()) "" else ".$decimalPattern"
            dfCustomDigit.applyPattern(pattern)
            return dfCustomDigit.format(this)
        }
    }
    return df2Digit.format(this)
}

fun formatWithCommas(value: Any): String {
    val formatter = NumberFormat.getNumberInstance(Locale.US).apply {
        maximumFractionDigits = Integer.MAX_VALUE
    }
    return if (value is String) {
        formatter.format(BigDecimal(value))
    } else {
        formatter.format(value)
    }
}

const val NO_DATA_TEXT = "N/A"
const val ZERO_VALUE_TEXT = "0.0"
const val NO_DATA_LONG = 0L
val NO_DATA_DOUBLE = Double.NaN