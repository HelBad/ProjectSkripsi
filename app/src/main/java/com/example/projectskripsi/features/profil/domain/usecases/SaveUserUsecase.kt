package com.example.projectskripsi.features.profil.domain.usecases

import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.UseCase
import com.example.projectskripsi.features.profil.domain.repositories.ProfilRepository
import io.reactivex.Flowable

class SaveUserUsecase(private val repository: ProfilRepository) :
    UseCase<Flowable<Resource<String?>>, SaveUserUsecase.SaveUserParams>() {
    override fun run(params: SaveUserParams) = repository.updateUser(
        params.id.toString(),
        params.nama.toString(),
        params.email.toString(),
        params.password.toString(),
        params.tglLahir.toString(),
        params.gender.toString(),
        params.alamat.toString(),
        params.telp.toString(),
        params.level.toString(),
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