package com.example.projectskripsi.features.checkout.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.UseCase
import com.example.projectskripsi.features.checkout.domain.entities.Keranjang
import com.example.projectskripsi.features.checkout.domain.entities.User
import com.example.projectskripsi.features.checkout.domain.usecases.BuatPesananUsecase
import com.example.projectskripsi.features.checkout.domain.usecases.GetDetailKeranjangUsecase
import com.example.projectskripsi.features.checkout.domain.usecases.GetKeranjangUsecase
import com.example.projectskripsi.features.checkout.domain.usecases.GetUserUsecase

class CheckoutViewModel(
    private val getUserUsecase: GetUserUsecase,
    private val getDetailKeranjangUsecase: GetDetailKeranjangUsecase,
    private val getKeranjangUsecase: GetKeranjangUsecase,
    private val buatPesananUsecase: BuatPesananUsecase,
) : ViewModel() {
    fun getUser(): LiveData<Resource<User?>> {
        return LiveDataReactiveStreams.fromPublisher(getUserUsecase.run(UseCase.NoParams()))
    }

    fun getDetailKeranjang(idUser: String): LiveData<Resource<Keranjang?>> {
        return LiveDataReactiveStreams.fromPublisher(
            getDetailKeranjangUsecase.run(
                GetDetailKeranjangUsecase.GetDetailKeranjangParams(idUser)
            )
        )
    }

    fun getKeranjang(idUser: String): LiveData<Resource<ArrayList<Keranjang>>> {
        return LiveDataReactiveStreams.fromPublisher(
            getKeranjangUsecase.run(
                GetKeranjangUsecase.GetKeranjangParams(idUser)
            )
        )
    }

    fun buatPesanan(
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
            buatPesananUsecase.run(
                BuatPesananUsecase.BuatPesananParams(
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