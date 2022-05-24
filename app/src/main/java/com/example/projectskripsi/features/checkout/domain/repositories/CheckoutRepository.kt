package com.example.projectskripsi.features.checkout.domain.repositories

import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.features.checkout.domain.entities.Keranjang
import com.example.projectskripsi.features.checkout.domain.entities.Menu
import com.example.projectskripsi.features.checkout.domain.entities.User
import io.reactivex.Flowable

interface CheckoutRepository {
    fun getUser(): Flowable<Resource<User?>>

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
    ): Flowable<Resource<String?>>

    fun getDetailKeranjang(idUser: String): Flowable<Resource<Keranjang?>>

    fun getKeranjang(idUser: String): Flowable<Resource<ArrayList<Keranjang>>>

    fun getDetailMenu(id: String): Flowable<Resource<Menu?>>
}