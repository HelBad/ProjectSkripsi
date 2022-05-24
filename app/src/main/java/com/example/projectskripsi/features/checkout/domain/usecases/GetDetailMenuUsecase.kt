package com.example.projectskripsi.features.checkout.domain.usecases

import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.UseCase
import com.example.projectskripsi.features.checkout.domain.entities.Keranjang
import com.example.projectskripsi.features.checkout.domain.entities.Menu
import com.example.projectskripsi.features.checkout.domain.repositories.CheckoutRepository
import io.reactivex.Flowable

class GetDetailMenuUsecase(private val repository: CheckoutRepository) :
    UseCase<Flowable<Resource<Menu?>>, GetDetailMenuUsecase.GetDetailMenuParams>() {
    override fun run(params: GetDetailMenuParams) =
        repository.getDetailMenu(params.id)

    class GetDetailMenuParams(
        val id: String
    )
}