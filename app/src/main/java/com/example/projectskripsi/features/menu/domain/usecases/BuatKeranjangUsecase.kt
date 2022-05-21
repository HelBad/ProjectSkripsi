package com.example.projectskripsi.features.menu.domain.usecases

import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.UseCase
import com.example.projectskripsi.features.menu.domain.repositories.MenuRepository
import io.reactivex.Flowable

class BuatKeranjangUsecase(private val repository: MenuRepository) :
    UseCase<Flowable<Resource<String?>>, BuatKeranjangUsecase.BuatKeranjangParams>() {
    override fun run(params: BuatKeranjangParams) = repository.buatKeranjang(
        params.idUser, params.idMenu, params.jumlah, params.total
    )

    data class BuatKeranjangParams(
        val idUser: String,
        val idMenu: String,
        val jumlah: String,
        val total: String
    )
}