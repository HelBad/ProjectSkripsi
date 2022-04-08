package com.example.projectskripsi.core.domain.repositories

import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.model.User
import io.reactivex.Flowable

interface AuthRepository {
    fun login(email: String, password: String): Flowable<Resource<User?>>

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