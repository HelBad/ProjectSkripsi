package com.example.projectskripsi.features.detail.domain.usecases

import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.UseCase
import com.example.projectskripsi.features.beranda.domain.entities.Menu
import com.example.projectskripsi.features.beranda.domain.repositories.BerandaRepository
import com.example.projectskripsi.features.detail.domain.repositories.DetailRepository
import io.reactivex.Flowable

class HapusPesananUsecase(private val repository: DetailRepository) :
    UseCase<Flowable<Resource<String?>>, HapusPesananUsecase.HapusPesananParams>() {
    override fun run(params: HapusPesananParams) = repository.hapusPesanan(
        params.idKeranjang, params.idUser
    )

    data class HapusPesananParams(
        val idKeranjang: String,
        val idUser: String
    )
}