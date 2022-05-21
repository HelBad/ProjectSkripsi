package com.example.projectskripsi.features.riwayat.domain.repositories

import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.features.riwayat.domain.entities.User
import com.example.projectskripsi.features.riwayat.domain.entities.Keranjang
import com.example.projectskripsi.features.riwayat.domain.entities.Menu
import com.example.projectskripsi.features.riwayat.domain.entities.Pesanan
import io.reactivex.Flowable

interface RiwayatRepository {
    fun getUser(): Flowable<Resource<User?>>

    fun getDetailPesanan(status: String, idPesanan: String): Flowable<Resource<Pesanan?>>

    fun getKeranjang(idKeranjang: String, idUser: String): Flowable<Resource<ArrayList<Keranjang>>>

    fun getDetailKeranjang(idKeranjang: String, idUser: String): Flowable<Resource<Keranjang?>>

    fun getMenu(idMenu: String): Flowable<Resource<Menu?>>
}