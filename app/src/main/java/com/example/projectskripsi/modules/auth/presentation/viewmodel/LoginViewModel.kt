package com.example.projectskripsi.modules.auth.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.modules.auth.domain.entities.User
import com.example.projectskripsi.modules.auth.domain.usecases.AuthUsecase

class LoginViewModel constructor(private val usecase: AuthUsecase) : ViewModel() {
    fun login(email: String, password: String) : LiveData<Resource<User?>> {
        return LiveDataReactiveStreams.fromPublisher(usecase.login(email, password))
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