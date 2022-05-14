package com.example.projectskripsi.modules.detail.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.modules.detail.domain.entities.Keranjang
import com.example.projectskripsi.modules.detail.domain.entities.Menu
import com.example.projectskripsi.modules.detail.domain.entities.User
import com.example.projectskripsi.modules.detail.domain.usecases.DetailUsecase

class DetailViewModel constructor(private val usecase: DetailUsecase) : ViewModel() {
    fun getDetailMenu(id: String) : LiveData<Resource<Menu?>> {
        return LiveDataReactiveStreams.fromPublisher(usecase.getDetailMenu(id))
    }

    fun getDetailKeranjang(idKeranjang: String, idUser: String) : LiveData<Resource<Keranjang?>> {
        return LiveDataReactiveStreams.fromPublisher(
            usecase.getDetailKeranjang(idKeranjang, idUser)
        )
    }

    fun hapusPesanan(idKeranjang: String, idUser: String) : LiveData<Resource<String?>> {
        return LiveDataReactiveStreams.fromPublisher(
            usecase.hapusPesanan(idKeranjang, idUser)
        )
    }

    fun buatPesanan(idUser: String, idMenu: String, jumlah: String, total: String) : LiveData<Resource<String?>> {
        return LiveDataReactiveStreams.fromPublisher(
            usecase.buatPesanan(idUser, idMenu, jumlah, total)
        )
    }

    fun updatePesanan(idKeranjang: String, jumlah: String, total: String, idUser: String) : LiveData<Resource<String?>> {
        return LiveDataReactiveStreams.fromPublisher(
            usecase.updatePesanan(idKeranjang, jumlah, total, idUser)
        )
    }

    fun getUser() : LiveData<Resource<User?>> {
        return LiveDataReactiveStreams.fromPublisher(usecase.getUser())
    }
}