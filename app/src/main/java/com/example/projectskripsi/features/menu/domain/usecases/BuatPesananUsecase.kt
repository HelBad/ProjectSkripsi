package com.example.projectskripsi.features.menu.domain.usecases

import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.UseCase
import com.example.projectskripsi.features.menu.domain.repositories.MenuRepository
import io.reactivex.Flowable

class BuatPesananUsecase(private val repository: MenuRepository) :
    UseCase<Flowable<Resource<String?>>, BuatPesananUsecase.BuatPesananParams>() {
    override fun run(params: BuatPesananParams) = repository.buatPesanan(
        params.idUser, params.idMenu, params.jumlah, params.total
    )

    data class BuatPesananParams(
        val idUser: String,
        val idMenu: String,
        val jumlah: String,
        val total: String
    )
}