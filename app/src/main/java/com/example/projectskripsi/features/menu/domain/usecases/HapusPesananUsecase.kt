package com.example.projectskripsi.features.menu.domain.usecases

import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.UseCase
import com.example.projectskripsi.features.menu.domain.repositories.MenuRepository
import io.reactivex.Flowable

class HapusPesananUsecase(private val repository: MenuRepository) :
    UseCase<Flowable<Resource<String?>>, HapusPesananUsecase.HapusPesananParams>() {
    override fun run(params: HapusPesananParams) = repository.hapusPesanan(
        params.idKeranjang, params.idUser
    )

    data class HapusPesananParams(
        val idKeranjang: String,
        val idUser: String
    )
}