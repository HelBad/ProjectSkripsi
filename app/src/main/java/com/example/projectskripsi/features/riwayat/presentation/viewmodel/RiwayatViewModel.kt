package com.example.projectskripsi.features.riwayat.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.Usecase
import com.example.projectskripsi.features.riwayat.domain.entities.Keranjang
import com.example.projectskripsi.features.riwayat.domain.entities.Menu
import com.example.projectskripsi.features.riwayat.domain.entities.Pesanan
import com.example.projectskripsi.features.riwayat.domain.entities.User
import com.example.projectskripsi.features.riwayat.domain.usecases.*

class RiwayatViewModel(
    private val getUserUsecase: GetUserUsecase,
    private val getUserRemoteUsecase: GetUserRemoteUsecase,
    private val getDetailPesananUsecase: GetDetailPesananUsecase,
    private val getKeranjangUsecase: GetKeranjangUsecase,
    private val getDetailKeranjangUsecase: GetDetailKeranjangUsecase,
    private val getMenuUsecase: GetMenuUsecase,
    private val updatePesananUsecase: UpdatePesananUsecase,
) : ViewModel() {
    fun getUser(): LiveData<Resource<User?>> {
        return LiveDataReactiveStreams.fromPublisher(getUserUsecase.run(Usecase.NoParams()))
    }

    fun getUser(id: String): LiveData<Resource<User?>> {
        return LiveDataReactiveStreams.fromPublisher(
            getUserRemoteUsecase.run(
                GetUserRemoteUsecase.GetUserRemoteParams(id)
            )
        )
    }

    fun getDetailPesanan(status: String, idUser: String): LiveData<Resource<Pesanan?>> {
        return LiveDataReactiveStreams.fromPublisher(
            getDetailPesananUsecase.run(
                GetDetailPesananUsecase.GetDetailPesananParams(status, idUser)
            )
        )
    }

    fun getKeranjang(
        idKeranjang: String,
        idUser: String
    ): LiveData<Resource<ArrayList<Keranjang>>> {
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

    fun updatePesanan(
        id: String,
        idUser: String,
        idKeranjang: String,
        catatan: String,
        waktu: String,
        lokasi: String,
        subtotal: String,
        ongkir: String,
        totalBayar: String,
        status: String,
        keterangan: String,
    ): LiveData<Resource<String?>> {
        return LiveDataReactiveStreams.fromPublisher(
            updatePesananUsecase.run(
                UpdatePesananUsecase.UpdatePesananParams(
                    id,
                    idUser,
                    idKeranjang,
                    catatan,
                    waktu,
                    lokasi,
                    subtotal,
                    ongkir,
                    totalBayar,
                    status,
                    keterangan
                )
            )
        )
    }
}