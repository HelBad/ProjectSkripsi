package com.example.projectskripsi.features.beranda.domain.usecases

import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.UseCase
import com.example.projectskripsi.features.beranda.domain.entities.Menu
import com.example.projectskripsi.features.beranda.domain.repositories.BerandaRepository
import io.reactivex.Flowable

class GetMenuUsecase(private val repository: BerandaRepository) :
    UseCase<Flowable<Resource<ArrayList<Menu>>>, UseCase.NoParams>() {
    override fun run(params: NoParams) = repository.getMenu()
}