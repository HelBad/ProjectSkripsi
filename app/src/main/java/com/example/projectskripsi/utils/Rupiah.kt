package com.example.projectskripsi.utils

import java.text.NumberFormat
import java.util.*

class Rupiah {
    companion object {
        fun format(value: Number): String {
            return "Rp. " + NumberFormat.getNumberInstance(Locale.US).format(value)
                .replace(",", ".")
        }
    }
}