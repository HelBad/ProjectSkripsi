package com.example.projectskripsi.features.auth.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.features.auth.domain.entities.User
import com.example.projectskripsi.features.auth.domain.usecases.RegisterUsecase
import com.example.projectskripsi.features.auth.domain.usecases.SaveUserUsecase

class RegisterViewModel constructor(
    private val registerUsecase: RegisterUsecase,
    private val saveUserUsecase: SaveUserUsecase,
) : ViewModel() {
    fun register(
        nama: String,
        email: String,
        password: String,
        tanggal: String,
        gender: String,
        alamat: String,
        telp: String
    ): LiveData<Resource<User?>> {
        return LiveDataReactiveStreams.fromPublisher(
            registerUsecase.run(
                RegisterUsecase.RegisterParams(
                    nama, email, password, tanggal, gender, alamat, telp
                )
            )
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
    ): LiveData<Resource<String?>> {
        return LiveDataReactiveStreams.fromPublisher(
            saveUserUsecase.run(
                SaveUserUsecase.SaveUserParams(
                    id,
                    nama,
                    email,
                    password,
                    tanggal,
                    gender,
                    alamat,
                    telp,
                    level
                )
            )
        )
    }
}