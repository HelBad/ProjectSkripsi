package com.example.projectskripsi.modules.beranda.domain.repositories

import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.modules.beranda.domain.entities.Menu
import com.example.projectskripsi.modules.beranda.domain.entities.Penyakit
import io.reactivex.Flowable

interface BerandaRepository {
    fun getMenu(): Flowable<Resource<ArrayList<Menu>>>

    fun getPenyakit(): Flowable<Resource<ArrayList<Penyakit>>>
}