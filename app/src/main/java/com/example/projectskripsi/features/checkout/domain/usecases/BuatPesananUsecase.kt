package com.example.projectskripsi.features.checkout.domain.usecases

import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.Usecase
import com.example.projectskripsi.features.checkout.domain.repositories.CheckoutRepository
import io.reactivex.Flowable

class BuatPesananUsecase(private val repository: CheckoutRepository) :
    Usecase<Flowable<Resource<String?>>, BuatPesananUsecase.BuatPesananParams>() {
    override fun run(params: BuatPesananParams) = repository.buatPesanan(
        params.idUser, params.idKeranjang, params.catatan, params.waktu, params.lokasi,
        params.subtotal, params.ongkir, params.totalBayar, params.status, params.keterangan
    )

    data class BuatPesananParams(
        val idUser: String,
        val idKeranjang: String,
        val catatan: String,
        val waktu: String,
        val lokasi: String,
        val subtotal: String,
        val ongkir: String,
        val totalBayar: String,
        val status: String,
        val keterangan: String
    )
}