package com.example.projectskripsi.features.riwayat.domain.usecases

import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.UseCase
import com.example.projectskripsi.features.checkout.domain.repositories.CheckoutRepository
import com.example.projectskripsi.features.menu.domain.repositories.MenuRepository
import com.example.projectskripsi.features.riwayat.domain.repositories.RiwayatRepository
import com.google.gson.annotations.SerializedName
import io.reactivex.Flowable

class UpdatePesananUsecase(private val repository: RiwayatRepository) :
    UseCase<Flowable<Resource<String?>>, UpdatePesananUsecase.UpdatePesananParams>() {
    override fun run(params: UpdatePesananParams) = repository.updatePesanan(
        params.id, params.idUser, params.idKeranjang, params.catatan, params.waktu, params.lokasi,
        params.subtotal, params.ongkir, params.totalBayar, params.status, params.keterangan
    )

    data class UpdatePesananParams(
        val id: String,
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