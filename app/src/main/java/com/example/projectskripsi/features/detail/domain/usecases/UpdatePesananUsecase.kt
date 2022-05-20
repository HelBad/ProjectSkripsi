package com.example.projectskripsi.features.detail.domain.usecases

import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.UseCase
import com.example.projectskripsi.features.beranda.domain.entities.Menu
import com.example.projectskripsi.features.beranda.domain.repositories.BerandaRepository
import com.example.projectskripsi.features.detail.domain.repositories.DetailRepository
import io.reactivex.Flowable

class UpdatePesananUsecase(private val repository: DetailRepository) :
    UseCase<Flowable<Resource<String?>>, UpdatePesananUsecase.UpdatePesananParams>() {
    override fun run(params: UpdatePesananParams) = repository.updatePesanan(
        params.idKeranjang, params.jumlah, params.total, params.idUser
    )

    data class UpdatePesananParams(
        val idKeranjang: String,
        val jumlah: String,
        val total: String,
        val idUser: String
    )
}