package com.example.projectskripsi.features.detail.domain.usecases

import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.UseCase
import com.example.projectskripsi.features.beranda.domain.entities.Menu
import com.example.projectskripsi.features.beranda.domain.repositories.BerandaRepository
import com.example.projectskripsi.features.detail.domain.entities.User
import com.example.projectskripsi.features.detail.domain.repositories.DetailRepository
import io.reactivex.Flowable

class GetUserUsecase(private val repository: DetailRepository) :
    UseCase<Flowable<Resource<User?>>, UseCase.NoParams>() {
    override fun run(params: NoParams) = repository.getUser()
}