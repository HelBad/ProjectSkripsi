package com.example.projectskripsi.features.auth.domain.usecases

import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.Usecase
import com.example.projectskripsi.features.auth.domain.entities.User
import com.example.projectskripsi.features.auth.domain.repositories.AuthRepository
import io.reactivex.Flowable

class GetUserUsecase(private val repository: AuthRepository) : Usecase<Flowable<Resource<User?>>, Usecase.NoParams>() {
    override fun run(params: NoParams) = repository.getUser()
}