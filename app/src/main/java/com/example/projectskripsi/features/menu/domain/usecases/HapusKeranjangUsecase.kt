package com.example.projectskripsi.features.menu.domain.usecases

import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.Usecase
import com.example.projectskripsi.features.menu.domain.repositories.MenuRepository
import io.reactivex.Flowable

class HapusKeranjangUsecase(private val repository: MenuRepository) :
    Usecase<Flowable<Resource<String?>>, HapusKeranjangUsecase.HapusKeranjangParams>() {
    override fun run(params: HapusKeranjangParams) = repository.hapusKeranjang(
        params.idKeranjang, params.idUser
    )

    data class HapusKeranjangParams(
        val idKeranjang: String,
        val idUser: String
    )
}