package com.example.projectskripsi.features.riwayat.domain.usecases

import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.Usecase
import com.example.projectskripsi.features.riwayat.domain.entities.Pesanan
import com.example.projectskripsi.features.riwayat.domain.repositories.RiwayatRepository
import io.reactivex.Flowable

class GetDetailPesananUsecase(private val repository: RiwayatRepository) :
    Usecase<Flowable<Resource<Pesanan?>>, GetDetailPesananUsecase.GetDetailPesananParams>() {
    override fun run(params: GetDetailPesananParams) =
        repository.getDetailPesanan(params.status, params.idUser)

    class GetDetailPesananParams(
        val status: String,
        val idUser: String
    )
}