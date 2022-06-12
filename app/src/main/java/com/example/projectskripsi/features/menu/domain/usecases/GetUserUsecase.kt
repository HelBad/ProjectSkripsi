package com.example.projectskripsi.features.menu.domain.usecases

import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.Usecase
import com.example.projectskripsi.features.menu.domain.entities.User
import com.example.projectskripsi.features.menu.domain.repositories.MenuRepository
import io.reactivex.Flowable

class GetUserUsecase(private val repository: MenuRepository) :
    Usecase<Flowable<Resource<User?>>, Usecase.NoParams>() {
    override fun run(params: NoParams) = repository.getUser()
}