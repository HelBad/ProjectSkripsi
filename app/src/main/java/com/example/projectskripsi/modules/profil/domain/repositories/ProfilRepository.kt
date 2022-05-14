package com.example.projectskripsi.modules.profil.domain.repositories

import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.modules.profil.domain.entities.User
import io.reactivex.Flowable

interface ProfilRepository {
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

    fun updateUser(
        id: String,
        nama: String,
        email: String,
        password: String,
        tglLahir: String,
        gender: String,
        alamat: String,
        telp: String,
        level: String,
    ): Flowable<Resource<String?>>
}