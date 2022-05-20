package com.example.projectskripsi.features.detail.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.UseCase
import com.example.projectskripsi.features.detail.domain.entities.Keranjang
import com.example.projectskripsi.features.detail.domain.entities.Menu
import com.example.projectskripsi.features.detail.domain.entities.User
import com.example.projectskripsi.features.detail.domain.usecases.*

class DetailViewModel constructor(
    private val getDetailMenuUsecase: GetDetailMenuUsecase,
    private val getDetailKeranjangUsecase: GetDetailKeranjangUsecase,
    private val hapusPesananUsecase: HapusPesananUsecase,
    private val buatPesananUsecase: BuatPesananUsecase,
    private val updatePesananUsecase: UpdatePesananUsecase,
    private val getUserUsecase: GetUserUsecase,
) : ViewModel() {
    fun getDetailMenu(id: String): LiveData<Resource<Menu?>> {
        return LiveDataReactiveStreams.fromPublisher(
            getDetailMenuUsecase.run(GetDetailMenuUsecase.GetDetailMenuParams(id))
        )
    }

    fun getDetailKeranjang(idKeranjang: String, idUser: String): LiveData<Resource<Keranjang?>> {
        return LiveDataReactiveStreams.fromPublisher(
            getDetailKeranjangUsecase.run(
                GetDetailKeranjangUsecase.GetDetailKeranjangParams(
                    idKeranjang, idUser
                )
            )
        )
    }

    fun hapusPesanan(idKeranjang: String, idUser: String): LiveData<Resource<String?>> {
        return LiveDataReactiveStreams.fromPublisher(
            hapusPesananUsecase.run(HapusPesananUsecase.HapusPesananParams(idKeranjang, idUser))
        )
    }

    fun buatPesanan(
        idUser: String,
        idMenu: String,
        jumlah: String,
        total: String
    ): LiveData<Resource<String?>> {
        return LiveDataReactiveStreams.fromPublisher(
            buatPesananUsecase.run(
                BuatPesananUsecase.BuatPesananParams(
                    idUser,
                    idMenu,
                    jumlah,
                    total
                )
            )
        )
    }

    fun updatePesanan(
        idKeranjang: String,
        jumlah: String,
        total: String,
        idUser: String
    ): LiveData<Resource<String?>> {
        return LiveDataReactiveStreams.fromPublisher(
            updatePesananUsecase.run(
                UpdatePesananUsecase.UpdatePesananParams(
                    idKeranjang,
                    jumlah,
                    total,
                    idUser
                )
            )
        )
    }

    fun getUser(): LiveData<Resource<User?>> {
        return LiveDataReactiveStreams.fromPublisher(
            getUserUsecase.run(UseCase.NoParams())
        )
    }
}