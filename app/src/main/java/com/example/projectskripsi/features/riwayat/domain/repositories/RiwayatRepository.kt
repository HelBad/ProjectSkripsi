package com.example.projectskripsi.features.riwayat.domain.repositories

import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.Response
import com.example.projectskripsi.features.riwayat.domain.entities.User
import com.example.projectskripsi.features.riwayat.domain.entities.Keranjang
import com.example.projectskripsi.features.riwayat.domain.entities.Menu
import com.example.projectskripsi.features.riwayat.domain.entities.Pesanan
import io.reactivex.Flowable

interface RiwayatRepository {
    fun getUser(): Flowable<Resource<User?>>

    fun getUser(id: String): Flowable<Resource<User?>>

    fun getDetailPesanan(status: String, idPesanan: String): Flowable<Resource<Pesanan?>>

    fun getKeranjang(idKeranjang: String, idUser: String): Flowable<Resource<ArrayList<Keranjang>>>

    fun getDetailKeranjang(idKeranjang: String, idUser: String): Flowable<Resource<Keranjang?>>

    fun getMenu(idMenu: String): Flowable<Resource<Menu?>>

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
    ): Flowable<Resource<String?>>
}