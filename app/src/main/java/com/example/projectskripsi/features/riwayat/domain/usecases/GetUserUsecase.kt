package com.example.projectskripsi.features.riwayat.domain.usecases

import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.UseCase
import com.example.projectskripsi.features.riwayat.domain.entities.User
import com.example.projectskripsi.features.riwayat.domain.repositories.RiwayatRepository
import io.reactivex.Flowable

class GetUserUsecase(private val repository: RiwayatRepository) :
    UseCase<Flowable<Resource<User?>>, UseCase.NoParams>() {
    override fun run(params: NoParams) = repository.getUser()
}