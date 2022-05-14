package com.example.projectskripsi.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

class Tanggal {
    companion object {
        @SuppressLint("SimpleDateFormat")
        fun format(value: Date, pattern: String? = "dd MMM yyyy") : String {
            return SimpleDateFormat(pattern).format(value.time)
        }
    }
}