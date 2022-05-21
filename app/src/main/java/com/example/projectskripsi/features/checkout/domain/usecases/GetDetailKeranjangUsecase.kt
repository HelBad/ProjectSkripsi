package com.example.projectskripsi.features.checkout.domain.usecases

import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.UseCase
import com.example.projectskripsi.features.checkout.domain.entities.Keranjang
import com.example.projectskripsi.features.checkout.domain.repositories.CheckoutRepository
import io.reactivex.Flowable

class GetDetailKeranjangUsecase(private val repository: CheckoutRepository) :
    UseCase<Flowable<Resource<Keranjang?>>, GetDetailKeranjangUsecase.GetDetailKeranjangParams>() {
    override fun run(params: GetDetailKeranjangParams) =
        repository.getDetailKeranjang(params.idUser)

    class GetDetailKeranjangParams(
        val idUser: String
    )
}