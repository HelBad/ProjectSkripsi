package com.example.projectskripsi.features.menu.domain.usecases

import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.Usecase
import com.example.projectskripsi.features.menu.domain.entities.Keranjang
import com.example.projectskripsi.features.menu.domain.repositories.MenuRepository
import io.reactivex.Flowable

class GetDetailKeranjangUsecase(private val repository: MenuRepository) :
    Usecase<Flowable<Resource<Keranjang?>>, GetDetailKeranjangUsecase.GetDetailKeranjangParams>() {
    override fun run(params: GetDetailKeranjangParams) = repository.getDetailKeranjang(
        params.idMenu, params.idUser
    )

    data class GetDetailKeranjangParams(
        val idMenu: String,
        val idUser: String,
    )
}