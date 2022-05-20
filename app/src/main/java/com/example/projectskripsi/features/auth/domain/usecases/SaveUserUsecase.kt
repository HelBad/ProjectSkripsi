package com.example.projectskripsi.features.auth.domain.usecases

import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.UseCase
import com.example.projectskripsi.features.auth.domain.repositories.AuthRepository
import io.reactivex.Flowable

class SaveUserUsecase(private val repository: AuthRepository) :
    UseCase<Flowable<Resource<String?>>, SaveUserUsecase.SaveUserParams>() {

    override fun run(params: SaveUserParams) = repository.saveUser(
        id = params.id,
        nama = params.nama,
        email = params.email,
        password = params.password,
        tglLahir = params.tglLahir,
        gender = params.gender,
        alamat = params.alamat,
        telp = params.telp,
        level = params.level,
    )

    data class SaveUserParams(
        val id: String?,
        val nama: String?,
        val email: String?,
        val password: String?,
        val tglLahir: String?,
        val gender: String?,
        val alamat: String?,
        val telp: String?,
        val level: String?
    )
}