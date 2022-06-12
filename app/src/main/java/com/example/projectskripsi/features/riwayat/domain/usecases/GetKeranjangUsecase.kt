package com.example.projectskripsi.features.riwayat.domain.usecases

import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.Usecase
import com.example.projectskripsi.features.riwayat.domain.entities.Keranjang
import com.example.projectskripsi.features.riwayat.domain.repositories.RiwayatRepository
import io.reactivex.Flowable

class GetKeranjangUsecase(private val repository: RiwayatRepository) :
    Usecase<Flowable<Resource<ArrayList<Keranjang>>>, GetKeranjangUsecase.GetKeranjangParams>() {
    override fun run(params: GetKeranjangParams) =
        repository.getKeranjang(params.idKeranjang, params.idUser)

    class GetKeranjangParams(
        val idKeranjang: String,
        val idUser: String
    )
}