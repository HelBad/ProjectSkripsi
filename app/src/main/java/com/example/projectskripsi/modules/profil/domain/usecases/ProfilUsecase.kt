package com.example.projectskripsi.modules.profil.domain.usecases

import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.modules.profil.domain.entities.User
import com.example.projectskripsi.modules.profil.domain.repositories.ProfilRepository
import io.reactivex.Flowable

class ProfilUsecase constructor(private val repository: ProfilRepository) {
    fun getUser() : Flowable<Resource<User?>> {
        return repository.getUser()
    }

    fun saveUser(id: String?, nama: String?, email: String?, password: String?, tglLahir: String?, gender: String?, alamat: String?, telp: String?, level: String?) : Flowable<Resource<String?>> {
        return repository.saveUser(id, nama, email, password, tglLahir, gender, alamat, telp, level)
    }

    fun updateUser(id: String, nama: String, email: String, password: String, tglLahir: String, gender: String, alamat: String, telp: String, level: String) : Flowable<Resource<String?>> {
        return repository.updateUser(id, nama, email, password, tglLahir, gender, alamat, telp, level)
    }
}