package com.example.projectskripsi.features.menu.domain.usecases

import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.Usecase
import com.example.projectskripsi.features.menu.domain.repositories.MenuRepository
import io.reactivex.Flowable

class HapusMenuUsecase(private val repository: MenuRepository) :
    Usecase<Flowable<Resource<String?>>, HapusMenuUsecase.HapusMenuParams>() {
    override fun run(params: HapusMenuParams) = repository.hapusMenu(params.idMenu)

    data class HapusMenuParams(
        val idMenu: String,
    )
}