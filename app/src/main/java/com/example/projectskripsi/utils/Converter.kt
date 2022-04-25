package com.example.projectskripsi.utils

import com.google.firebase.database.DataSnapshot
import com.google.gson.Gson

class Converter {
    companion object {
        private val gson = Gson()

        fun toJson(snap: DataSnapshot) : String {
            return gson.toJson(snap.value)
        }

        fun <T> toObject(snap: DataSnapshot, type: Class<T>) : T? {
            return gson.fromJson(toJson(snap), type)
        }
    }
}