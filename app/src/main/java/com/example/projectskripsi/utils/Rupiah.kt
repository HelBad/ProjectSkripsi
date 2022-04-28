package com.example.projectskripsi.utils

import java.text.DecimalFormat
import java.text.NumberFormat

class Rupiah {
    companion object {
        private val formatNumber = DecimalFormat("#,###")

        fun format(value: Number) : String {
            return "Rp. " + formatNumber.format(value) + ",00"
        }
    }
}