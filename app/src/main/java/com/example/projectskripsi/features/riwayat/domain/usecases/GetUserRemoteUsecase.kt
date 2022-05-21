package com.example.projectskripsi.features.riwayat.domain.usecases

import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.UseCase
import com.example.projectskripsi.features.riwayat.domain.entities.User
import com.example.projectskripsi.features.riwayat.domain.repositories.RiwayatRepository
import io.reactivex.Flowable

class GetUserRemoteUsecase(private val repository: RiwayatRepository) :
    UseCase<Flowable<Resource<User?>>, GetUserRemoteUsecase.GetUserRemoteParams>() {
    override fun run(params: GetUserRemoteParams) = repository.getUser(params.id)

    class GetUserRemoteParams(
        val id: String
    )
}