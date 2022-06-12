package com.example.projectskripsi.features.profil.domain.usecases

import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.Usecase
import com.example.projectskripsi.features.profil.domain.entities.User
import com.example.projectskripsi.features.profil.domain.repositories.ProfilRepository
import io.reactivex.Flowable

class GetUserUsecase(private val repository: ProfilRepository) :
    Usecase<Flowable<Resource<User?>>, Usecase.NoParams>() {
    override fun run(params: NoParams) = repository.getUser()
}