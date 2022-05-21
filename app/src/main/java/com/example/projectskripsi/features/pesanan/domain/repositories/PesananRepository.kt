package com.example.projectskripsi.features.pesanan.domain.repositories

import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.features.pesanan.domain.entities.Keranjang
import com.example.projectskripsi.features.pesanan.domain.entities.Pesanan
import com.example.projectskripsi.features.pesanan.domain.entities.User
import io.reactivex.Flowable

interface PesananRepository {
    fun getUser(): Flowable<Resource<User?>>

    fun getPesanan(status: String, idUser: String?): Flowable<Resource<ArrayList<Pesanan>>>
}