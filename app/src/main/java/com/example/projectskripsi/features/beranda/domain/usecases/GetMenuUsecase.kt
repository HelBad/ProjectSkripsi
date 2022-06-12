package com.example.projectskripsi.features.beranda.domain.usecases

import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.Usecase
import com.example.projectskripsi.features.beranda.domain.entities.Menu
import com.example.projectskripsi.features.beranda.domain.repositories.BerandaRepository
import io.reactivex.Flowable

class GetMenuUsecase(private val repository: BerandaRepository) :
    Usecase<Flowable<Resource<ArrayList<Menu>>>, Usecase.NoParams>() {
    override fun run(params: NoParams) = repository.getMenu()
}