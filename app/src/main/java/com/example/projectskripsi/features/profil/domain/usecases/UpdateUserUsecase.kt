package com.example.projectskripsi.features.profil.domain.usecases

import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.Usecase
import com.example.projectskripsi.features.profil.domain.repositories.ProfilRepository
import io.reactivex.Flowable

class UpdateUserUsecase(private val repository: ProfilRepository) :
    Usecase<Flowable<Resource<String?>>, UpdateUserUsecase.UpdateUserParams>() {
    override fun run(params: UpdateUserParams) = repository.updateUser(
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

    data class UpdateUserParams(
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