package com.example.projectskripsi.modules.auth.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.modules.auth.data.models.User
import com.example.projectskripsi.modules.auth.domain.usecases.AuthUsecase

class AuthViewModel constructor(private val usecase: AuthUsecase) : ViewModel() {
    fun login(email: String, password: String) : LiveData<Resource<User?>> {
        return LiveDataReactiveStreams.fromPublisher(usecase.login(email, password))
    }

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
}