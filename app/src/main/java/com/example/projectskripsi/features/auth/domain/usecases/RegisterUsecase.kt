package com.example.projectskripsi.features.auth.domain.usecases

import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.Usecase
import com.example.projectskripsi.features.auth.domain.entities.User
import com.example.projectskripsi.features.auth.domain.repositories.AuthRepository
import io.reactivex.Flowable

class RegisterUsecase(private val repository: AuthRepository) :
    Usecase<Flowable<Resource<User?>>, RegisterUsecase.RegisterParams>() {

    override fun run(params: RegisterParams) = repository.register(
        nama = params.nama.toString(),
        email = params.email.toString(),
        password = params.password.toString(),
        tanggal = params.tglLahir.toString(),
        gender = params.gender.toString(),
        alamat = params.alamat.toString(),
        telp = params.telp.toString(),
    )

    data class RegisterParams(
        val nama: String?,
        val email: String?,
        val password: String?,
        val tglLahir: String?,
        val gender: String?,
        val alamat: String?,
        val telp: String?
    )
}