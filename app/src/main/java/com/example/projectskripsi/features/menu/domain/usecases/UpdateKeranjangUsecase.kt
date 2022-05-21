package com.example.projectskripsi.features.menu.domain.usecases

import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.UseCase
import com.example.projectskripsi.features.menu.domain.repositories.MenuRepository
import io.reactivex.Flowable

class UpdateKeranjangUsecase(private val repository: MenuRepository) :
    UseCase<Flowable<Resource<String?>>, UpdateKeranjangUsecase.UpdateKeranjangParams>() {
    override fun run(params: UpdateKeranjangParams) = repository.updateKeranjang(
        params.idKeranjang, params.jumlah, params.total, params.idUser
    )

    data class UpdateKeranjangParams(
        val idKeranjang: String,
        val jumlah: String,
        val total: String,
        val idUser: String
    )
}