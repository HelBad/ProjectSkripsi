package com.example.projectskripsi.features.auth.domain.repositories

import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.features.auth.domain.entities.User
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

    fun getUser(): Flowable<Resource<User?>>

    fun saveUser(
        id: String?,
        nama: String?,
        email: String?,
        password: String?,
        tglLahir: String?,
        gender: String?,
        alamat: String?,
        telp: String?,
        level: String?,
    ): Flowable<Resource<String?>>
}