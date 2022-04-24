package com.example.projectskripsi.modules.beranda.domain.repositories

import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.modules.beranda.data.models.Menu
import com.example.projectskripsi.modules.beranda.data.models.Penyakit
import io.reactivex.Flowable

interface BerandaRepository {
    fun getMenu(): Flowable<Resource<ArrayList<Menu>>>

    fun getPenyakit(): Flowable<Resource<ArrayList<Penyakit>>>
}