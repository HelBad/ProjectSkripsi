package com.example.projectskripsi.features.riwayat.domain.usecases

import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.Usecase
import com.example.projectskripsi.features.riwayat.domain.entities.Keranjang
import com.example.projectskripsi.features.riwayat.domain.repositories.RiwayatRepository
import io.reactivex.Flowable

class GetDetailKeranjangUsecase(private val repository: RiwayatRepository) :
    Usecase<Flowable<Resource<Keranjang?>>, GetDetailKeranjangUsecase.GetDetailKeranjangParams>() {
    override fun run(params: GetDetailKeranjangParams) =
        repository.getDetailKeranjang(params.idKeranjang, params.idUser)

    class GetDetailKeranjangParams(
        val idKeranjang: String,
        val idUser: String
    )
}