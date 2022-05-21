package com.example.projectskripsi.features.pesanan.domain.usecases

import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.UseCase
import com.example.projectskripsi.features.pesanan.domain.entities.Pesanan
import com.example.projectskripsi.features.pesanan.domain.repositories.PesananRepository
import io.reactivex.Flowable

class GetPesananUsecase(private val repository: PesananRepository) :
    UseCase<Flowable<Resource<ArrayList<Pesanan>>>, GetPesananUsecase.GetPesananParams>() {
    override fun run(params: GetPesananParams) = repository.getPesanan(params.status, params.idUser)

    class GetPesananParams(
        val status: String,
        val idUser: String?
    )
}