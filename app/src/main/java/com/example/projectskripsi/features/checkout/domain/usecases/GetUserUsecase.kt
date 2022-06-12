package com.example.projectskripsi.features.checkout.domain.usecases

import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.Usecase
import com.example.projectskripsi.features.checkout.domain.entities.User
import com.example.projectskripsi.features.checkout.domain.repositories.CheckoutRepository
import io.reactivex.Flowable

class GetUserUsecase(private val repository: CheckoutRepository) :
    Usecase<Flowable<Resource<User?>>, Usecase.NoParams>() {
    override fun run(params: NoParams) = repository.getUser()
}