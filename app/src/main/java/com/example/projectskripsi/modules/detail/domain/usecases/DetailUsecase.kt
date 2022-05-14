package com.example.projectskripsi.modules.detail.domain.usecases

import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.modules.detail.domain.entities.Keranjang
import com.example.projectskripsi.modules.detail.domain.entities.Menu
import com.example.projectskripsi.modules.detail.domain.entities.User
import com.example.projectskripsi.modules.detail.domain.repositories.DetailRepository
import io.reactivex.Flowable

class DetailUsecase constructor(private val repository: DetailRepository) {
    fun getDetailMenu(id: String) : Flowable<Resource<Menu?>> {
        return repository.getDetailMenu(id)
    }

    fun getDetailKeranjang(idKeranjang: String, idUser: String) : Flowable<Resource<Keranjang?>> {
        return repository.getDetailKeranjang(idKeranjang, idUser)
    }

    fun hapusPesanan(idKeranjang: String, idUser: String) : Flowable<Resource<String?>> {
        return repository.hapusPesanan(idKeranjang, idUser)
    }

    fun buatPesanan(idUser: String, idMenu: String, jumlah: String, total: String) : Flowable<Resource<String?>> {
        return repository.buatPesanan(idUser, idMenu, jumlah, total)
    }

    fun updatePesanan(idKeranjang: String, jumlah: String, total: String, idUser: String) : Flowable<Resource<String?>> {
        return repository.updatePesanan(idKeranjang, jumlah, total, idUser)
    }

    fun getUser() : Flowable<Resource<User?>> {
        return repository.getUser()
    }
}