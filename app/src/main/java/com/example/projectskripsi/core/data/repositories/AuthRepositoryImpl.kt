package com.example.projectskripsi.core.data.repositories

import com.example.projectskripsi.core.data.source.remote.AuthRemoteDataSource
import com.example.projectskripsi.core.domain.repositories.AuthRepository
import com.example.projectskripsi.model.User

class AuthRepositoryImpl constructor(
    private val remoteDataSource: AuthRemoteDataSource
) : AuthRepository {

    override fun login(email: String, password: String): User? {
        return remoteDataSource.login(email, password)
    }

    override fun register(nama: String, email: String, password: String, tanggal: String, gender: String, alamat: String, telp: String): User? {
        return remoteDataSource.register(nama, email, password, tanggal, gender, alamat, telp)
    }
}