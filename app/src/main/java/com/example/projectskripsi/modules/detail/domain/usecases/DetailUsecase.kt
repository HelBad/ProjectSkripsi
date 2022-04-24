package com.example.projectskripsi.modules.detail.domain.usecases

import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.modules.beranda.data.models.Menu
import com.example.projectskripsi.modules.detail.domain.repositories.DetailRepository
import io.reactivex.Flowable

class DetailUsecase constructor(private val repository: DetailRepository) {
    fun getDetailMenu(id: String) : Flowable<Resource<Menu?>> {
        return repository.getDetailMenu(id)
    }
}