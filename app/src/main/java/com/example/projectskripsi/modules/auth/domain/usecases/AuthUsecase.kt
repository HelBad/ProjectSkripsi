package com.example.projectskripsi.modules.auth.domain.usecases

import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.modules.auth.domain.entities.User
import com.example.projectskripsi.modules.auth.domain.repositories.AuthRepository
import io.reactivex.Flowable

class AuthUsecase constructor(private val repository: AuthRepository) {
    fun login(email: String, password: String) : Flowable<Resource<User?>> {
        return repository.login(email, password)
    }

    fun register(nama: String, email: String, password: String, tanggal: String, gender: String, alamat: String, telp: String) :  Flowable<Resource<User?>> {
        return repository.register(nama, email, password, tanggal, gender, alamat, telp)
    }

    fun getUser() : Flowable<Resource<User?>> {
        return repository.getUser()
    }

    fun saveUser(id: String?, nama: String?, email: String?, password: String?, tglLahir: String?, gender: String?, alamat: String?, telp: String?, level: String?) : Flowable<Resource<String?>> {
        return repository.saveUser(id, nama, email, password, tglLahir, gender, alamat, telp, level)
    }
}