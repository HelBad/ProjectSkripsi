package com.example.projectskripsi.modules.auth.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.modules.auth.domain.entities.User
import com.example.projectskripsi.modules.auth.domain.usecases.AuthUsecase

class RegisterViewModel constructor(private val usecase: AuthUsecase) : ViewModel() {
    fun register(
        nama: String,
        email: String,
        password: String,
        tanggal: String,
        gender: String,
        alamat: String,
        telp: String
    ) : LiveData<Resource<User?>> {
        return LiveDataReactiveStreams.fromPublisher(
            usecase.register(nama, email, password, tanggal, gender, alamat, telp)
        )
    }

    fun saveUser(
        id: String?,
        nama: String?,
        email: String?,
        password: String?,
        tanggal: String?,
        gender: String?,
        alamat: String?,
        telp: String?,
        level: String?
    ) : LiveData<Resource<String?>> {
        return LiveDataReactiveStreams.fromPublisher(
            usecase.saveUser(id, nama, email, password, tanggal, gender, alamat, telp, level)
        )
    }
}