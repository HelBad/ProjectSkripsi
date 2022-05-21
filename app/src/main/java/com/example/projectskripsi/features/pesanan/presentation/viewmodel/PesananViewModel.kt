package com.example.projectskripsi.features.pesanan.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.UseCase
import com.example.projectskripsi.features.pesanan.domain.entities.Keranjang
import com.example.projectskripsi.features.pesanan.domain.entities.Pesanan
import com.example.projectskripsi.features.pesanan.domain.entities.User
import com.example.projectskripsi.features.pesanan.domain.usecases.*

class PesananViewModel(
    private val getUserUsecase: GetUserUsecase,
    private val getPesananUsecase: GetPesananUsecase
) : ViewModel() {
    fun getUser(): LiveData<Resource<User?>> {
        return LiveDataReactiveStreams.fromPublisher(getUserUsecase.run(UseCase.NoParams()))
    }

    fun getPesanan(status: String, idUser: String?): LiveData<Resource<ArrayList<Pesanan>>> {
        return LiveDataReactiveStreams.fromPublisher(
            getPesananUsecase.run(
                GetPesananUsecase.GetPesananParams(status, idUser)
            )
        )
    }
}