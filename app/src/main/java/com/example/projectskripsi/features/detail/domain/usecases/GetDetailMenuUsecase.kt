package com.example.projectskripsi.features.detail.domain.usecases

import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.UseCase
import com.example.projectskripsi.features.detail.domain.entities.Menu
import com.example.projectskripsi.features.detail.domain.repositories.DetailRepository
import io.reactivex.Flowable

class GetDetailMenuUsecase(private val repository: DetailRepository) :
    UseCase<Flowable<Resource<Menu?>>, GetDetailMenuUsecase.GetDetailMenuParams>() {
    override fun run(params: GetDetailMenuParams) = repository.getDetailMenu(params.id)

    data class GetDetailMenuParams(
        val id: String,
    )
}

