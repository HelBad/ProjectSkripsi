package com.example.projectskripsi.features.detail.domain.repositories

import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.features.detail.domain.entities.Keranjang
import com.example.projectskripsi.features.detail.domain.entities.Menu
import com.example.projectskripsi.features.detail.domain.entities.User
import io.reactivex.Flowable

interface DetailRepository {
    fun getDetailMenu(id: String): Flowable<Resource<Menu?>>

    fun getDetailKeranjang(idKeranjang: String, idUser: String): Flowable<Resource<Keranjang?>>

    fun hapusPesanan(idKeranjang: String, idUser: String): Flowable<Resource<String?>>

    fun buatPesanan(idUser: String, idMenu: String, jumlah: String, total: String): Flowable<Resource<String?>>

    fun updatePesanan(idKeranjang: String, jumlah: String, total: String, idUser: String): Flowable<Resource<String?>>

    fun getUser(): Flowable<Resource<User?>>
}