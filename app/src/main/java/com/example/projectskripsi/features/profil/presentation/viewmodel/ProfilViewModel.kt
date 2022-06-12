package com.example.projectskripsi.features.profil.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.Usecase
import com.example.projectskripsi.features.profil.domain.entities.User
import com.example.projectskripsi.features.profil.domain.usecases.GetUserUsecase
import com.example.projectskripsi.features.profil.domain.usecases.SaveUserUsecase
import com.example.projectskripsi.features.profil.domain.usecases.UpdateUserUsecase

class ProfilViewModel constructor(
    private val getUserUsecase: GetUserUsecase,
    private val saveUserUsecase: SaveUserUsecase,
    private val updateUserUsecase: UpdateUserUsecase,
) : ViewModel() {
    fun getUser(): LiveData<Resource<User?>> {
        return LiveDataReactiveStreams.fromPublisher(getUserUsecase.run(Usecase.NoParams()))
    }

    fun saveUser(
        id: String?,
        nama: String?,
        email: String?,
        password: String?,
        tglLahir: String?,
        gender: String?,
        alamat: String?,
        telp: String?,
        level: String?
    ): LiveData<Resource<String?>> {
        return LiveDataReactiveStreams.fromPublisher(
            saveUserUsecase.run(
                SaveUserUsecase.SaveUserParams(
                    id, nama, email, password, tglLahir, gender, alamat, telp, level
                )
            )
        )
    }

    fun updateUser(
        id: String,
        nama: String,
        email: String,
        password: String,
        tglLahir: String,
        gender: String,
        alamat: String,
        telp: String,
        level: String
    ): LiveData<Resource<String?>> {
        return LiveDataReactiveStreams.fromPublisher(
            updateUserUsecase.run(
                UpdateUserUsecase.UpdateUserParams(
                    id, nama, email, password, tglLahir, gender, alamat, telp, level
                )
            )
        )
    }
}