package com.example.projectskripsi.features.menu.domain.usecases

import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.UseCase
import com.example.projectskripsi.features.menu.domain.entities.Menu
import com.example.projectskripsi.features.menu.domain.repositories.MenuRepository
import io.reactivex.Flowable

class GetDetailMenuUsecase(private val repository: MenuRepository) :
    UseCase<Flowable<Resource<Menu?>>, GetDetailMenuUsecase.GetDetailMenuParams>() {
    override fun run(params: GetDetailMenuParams) = repository.getDetailMenu(params.id)

    data class GetDetailMenuParams(
        val id: String,
    )
}

