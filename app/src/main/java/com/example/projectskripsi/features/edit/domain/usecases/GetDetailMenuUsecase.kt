package com.example.projectskripsi.features.edit.domain.usecases

import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.UseCase
import com.example.projectskripsi.features.edit.domain.repositories.EditRepository
import com.example.projectskripsi.features.edit.domain.entities.Menu
import io.reactivex.Flowable

class GetDetailMenuUsecase(private val repository: EditRepository) :
    UseCase<Flowable<Resource<Menu?>>, GetDetailMenuUsecase.GetDetailMenuParams>() {
    override fun run(params: GetDetailMenuParams) = repository.getDetailMenu(params.id)

    data class GetDetailMenuParams(
        val id: String,
    )
}

