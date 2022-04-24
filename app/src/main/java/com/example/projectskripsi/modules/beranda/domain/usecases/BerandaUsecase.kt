package com.example.projectskripsi.modules.beranda.domain.usecases

import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.modules.beranda.data.models.Menu
import com.example.projectskripsi.modules.beranda.data.models.Penyakit
import com.example.projectskripsi.modules.beranda.domain.repositories.BerandaRepository
import io.reactivex.Flowable

class BerandaUsecase constructor(private val repository: BerandaRepository) {
    fun getMenu() : Flowable<Resource<ArrayList<Menu>>> {
        return repository.getMenu()
    }

    fun getPenyakit() : Flowable<Resource<ArrayList<Penyakit>>> {
        return repository.getPenyakit()
    }
}