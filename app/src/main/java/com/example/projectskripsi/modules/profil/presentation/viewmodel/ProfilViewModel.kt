package com.example.projectskripsi.modules.profil.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.modules.profil.domain.entities.User
import com.example.projectskripsi.modules.profil.domain.usecases.ProfilUsecase

class ProfilViewModel constructor(private val usecase: ProfilUsecase) : ViewModel() {
    fun getUser() : LiveData<Resource<User?>> {
        return LiveDataReactiveStreams.fromPublisher(usecase.getUser())
    }

    fun saveUser(id: String?, nama: String?, email: String?, password: String?, tglLahir: String?, gender: String?, alamat: String?, telp: String?, level: String?) : LiveData<Resource<String?>> {
        return LiveDataReactiveStreams.fromPublisher(
            usecase.saveUser(id, nama, email, password, tglLahir, gender, alamat, telp, level)
        )
    }

    fun updateUser(id: String, nama: String, email: String, password: String, tglLahir: String, gender: String, alamat: String, telp: String, level: String) : LiveData<Resource<String?>> {
        return LiveDataReactiveStreams.fromPublisher(
            usecase.updateUser(id, nama, email, password, tglLahir, gender, alamat, telp, level)
        )
    }
}