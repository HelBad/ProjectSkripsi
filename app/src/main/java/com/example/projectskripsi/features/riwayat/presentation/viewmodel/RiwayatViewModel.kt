package com.example.projectskripsi.features.riwayat.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.UseCase
import com.example.projectskripsi.features.riwayat.domain.entities.Keranjang
import com.example.projectskripsi.features.riwayat.domain.entities.Menu
import com.example.projectskripsi.features.riwayat.domain.entities.Pesanan
import com.example.projectskripsi.features.riwayat.domain.entities.User
import com.example.projectskripsi.features.riwayat.domain.usecases.*

class RiwayatViewModel(
    private val getUserUsecase: GetUserUsecase,
    private val getDetailPesananUsecase: GetDetailPesananUsecase,
    private val getKeranjangUsecase: GetKeranjangUsecase,
    private val getDetailKeranjangUsecase: GetDetailKeranjangUsecase,
    private val getMenuUsecase: GetMenuUsecase,
) : ViewModel() {
    fun getUser(): LiveData<Resource<User?>> {
        return LiveDataReactiveStreams.fromPublisher(getUserUsecase.run(UseCase.NoParams()))
    }

    fun getDetailPesanan(status: String, idUser: String): LiveData<Resource<Pesanan?>> {
        return LiveDataReactiveStreams.fromPublisher(
            getDetailPesananUsecase.run(
                GetDetailPesananUsecase.GetDetailPesananParams(status, idUser)
            )
        )
    }

    fun getKeranjang(idKeranjang: String, idUser: String): LiveData<Resource<ArrayList<Keranjang>>> {
        return LiveDataReactiveStreams.fromPublisher(
            getKeranjangUsecase.run(
                GetKeranjangUsecase.GetKeranjangParams(idKeranjang, idUser)
            )
        )
    }

    fun getDetailKeranjang(idKeranjang: String, idUser: String): LiveData<Resource<Keranjang?>> {
        return LiveDataReactiveStreams.fromPublisher(
            getDetailKeranjangUsecase.run(
                GetDetailKeranjangUsecase.GetDetailKeranjangParams(idKeranjang, idUser)
            )
        )
    }

    fun getMenu(idMenu: String): LiveData<Resource<Menu?>> {
        return LiveDataReactiveStreams.fromPublisher(
            getMenuUsecase.run(GetMenuUsecase.GetMenuParams(idMenu))
        )
    }
}