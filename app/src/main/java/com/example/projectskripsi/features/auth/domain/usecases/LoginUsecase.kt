package com.example.projectskripsi.features.auth.domain.usecases

import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.UseCase
import com.example.projectskripsi.features.auth.domain.entities.User
import com.example.projectskripsi.features.auth.domain.repositories.AuthRepository
import io.reactivex.Flowable

class LoginUsecase constructor(private val repository: AuthRepository) :
    UseCase<Flowable<Resource<User?>>, LoginUsecase.LoginParams>() {

    override fun run(params: LoginParams) = repository.login(
        email = params.email,
        password = params.password,
    )

    data class LoginParams(
        val email: String,
        val password: String,
    )
}