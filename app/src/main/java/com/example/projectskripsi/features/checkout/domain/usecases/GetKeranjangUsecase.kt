package com.example.projectskripsi.features.checkout.domain.usecases

import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.UseCase
import com.example.projectskripsi.features.checkout.domain.repositories.CheckoutRepository
import com.example.projectskripsi.features.checkout.domain.entities.Keranjang
import io.reactivex.Flowable

class GetKeranjangUsecase(private val repository: CheckoutRepository) :
    UseCase<Flowable<Resource<ArrayList<Keranjang>>>, GetKeranjangUsecase.GetKeranjangParams>() {
    override fun run(params: GetKeranjangParams) =
        repository.getKeranjang(params.idUser)

    class GetKeranjangParams(
        val idUser: String
    )
}