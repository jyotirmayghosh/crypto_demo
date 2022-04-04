package com.jyotirmayg.cryptodemo.utilities

import java.text.DecimalFormat
import java.util.*

/**
 * @author jyoti
 * @created on 04-04-2022
 */

fun numberFormat(amount: String): String? {
    val myFormatter = DecimalFormat("#,##,###")

    var formatedAmount: String? = "amountFormatError"
    try {
        formatedAmount = if (amount.contains(".")) {
            val rupees: String = amount.split(".")[0]
            val paisa: String = amount.split(".")[1]
            val number = rupees.toLong()
            myFormatter.format(number) + "." + paisa
        } else {
            val number: Long = amount.toLong()
            myFormatter.format(number)
        }
    } catch (e: NumberFormatException) {
        Print.printStackTrace(e)
    }
    return formatedAmount
}