package com.example.projectskripsi.core.domain.repositories

import com.example.projectskripsi.model.User

interface AuthRepository {
    fun login(email: String, password: String): User?

    fun register(
        nama: String,
        email: String,
        password: String,
        tanggal: String,
        gender: String,
        alamat: String,
        telp: String
    ): User?
}