package com.example.projectskripsi.features.profil.domain.usecases

import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.UseCase
import com.example.projectskripsi.features.beranda.domain.entities.Menu
import com.example.projectskripsi.features.beranda.domain.repositories.BerandaRepository
import com.example.projectskripsi.features.detail.domain.repositories.DetailRepository
import com.example.projectskripsi.features.profil.domain.entities.User
import com.example.projectskripsi.features.profil.domain.repositories.ProfilRepository
import io.reactivex.Flowable

class GetUserUsecase(private val repository: ProfilRepository) :
    UseCase<Flowable<Resource<User?>>, UseCase.NoParams>() {
    override fun run(params: NoParams) = repository.getUser()
}