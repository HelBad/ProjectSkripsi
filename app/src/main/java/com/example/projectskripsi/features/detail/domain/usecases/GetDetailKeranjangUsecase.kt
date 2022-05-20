package com.example.projectskripsi.features.detail.domain.usecases

import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.UseCase
import com.example.projectskripsi.features.detail.domain.entities.Keranjang
import com.example.projectskripsi.features.detail.domain.repositories.DetailRepository
import io.reactivex.Flowable

class GetDetailKeranjangUsecase(private val repository: DetailRepository) :
    UseCase<Flowable<Resource<Keranjang?>>, GetDetailKeranjangUsecase.GetDetailKeranjangParams>() {
    override fun run(params: GetDetailKeranjangParams) = repository.getDetailKeranjang(
        params.idKeranjang, params.idUser
    )

    data class GetDetailKeranjangParams(
        val idKeranjang: String,
        val idUser: String,
    )
}