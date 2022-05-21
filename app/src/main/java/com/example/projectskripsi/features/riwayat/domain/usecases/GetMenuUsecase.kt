package com.example.projectskripsi.features.riwayat.domain.usecases

import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.UseCase
import com.example.projectskripsi.features.riwayat.domain.entities.Keranjang
import com.example.projectskripsi.features.riwayat.domain.entities.Menu
import com.example.projectskripsi.features.riwayat.domain.repositories.RiwayatRepository
import io.reactivex.Flowable

class GetMenuUsecase(private val repository: RiwayatRepository) :
    UseCase<Flowable<Resource<Menu?>>, GetMenuUsecase.GetMenuParams>() {
    override fun run(params: GetMenuParams) = repository.getMenu(params.idMenu)

    class GetMenuParams(
        val idMenu: String,
    )
}