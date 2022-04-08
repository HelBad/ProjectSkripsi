package com.example.projectskripsi.core.domain.usecases

import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.domain.repositories.AuthRepository
import com.example.projectskripsi.model.User
import io.reactivex.Flowable

class AuthUsecase constructor(private val repository: AuthRepository) {
    fun login(email: String, password: String) : Flowable<Resource<User?>> {
        return repository.login(email, password)
    }

    fun register(nama: String, email: String, password: String, tanggal: String, gender: String, alamat: String, telp: String) =
        repository.register(nama, email, password, tanggal, gender, alamat, telp)
}