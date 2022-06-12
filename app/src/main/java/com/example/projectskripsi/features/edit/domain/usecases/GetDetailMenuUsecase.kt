package com.example.projectskripsi.features.edit.domain.usecases

import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.Usecase
import com.example.projectskripsi.features.edit.domain.repositories.EditRepository
import com.example.projectskripsi.features.edit.domain.entities.Menu
import io.reactivex.Flowable

class GetDetailMenuUsecase(private val repository: EditRepository) :
    Usecase<Flowable<Resource<Menu?>>, GetDetailMenuUsecase.GetDetailMenuParams>() {
    override fun run(params: GetDetailMenuParams) = repository.getDetailMenu(params.id_menu)

    data class GetDetailMenuParams(
        val id_menu: String,
    )
}

