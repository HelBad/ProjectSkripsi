package com.example.projectskripsi.modules.auth.domain.repositories

import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.modules.auth.data.models.User
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
    ): Flowable<Resource<User?>>
}