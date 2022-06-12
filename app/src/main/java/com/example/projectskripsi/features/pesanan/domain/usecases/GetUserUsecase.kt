package com.example.projectskripsi.features.pesanan.domain.usecases

import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.Usecase
import com.example.projectskripsi.features.pesanan.domain.entities.User
import com.example.projectskripsi.features.pesanan.domain.repositories.PesananRepository
import io.reactivex.Flowable

class GetUserUsecase(private val repository: PesananRepository) :
    Usecase<Flowable<Resource<User?>>, Usecase.NoParams>() {
    override fun run(params: NoParams) = repository.getUser()
}