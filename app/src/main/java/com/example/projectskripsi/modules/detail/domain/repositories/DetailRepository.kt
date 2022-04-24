package com.example.projectskripsi.modules.detail.domain.repositories

import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.modules.beranda.data.models.Menu
import com.example.projectskripsi.modules.beranda.data.models.Penyakit
import io.reactivex.Flowable

interface DetailRepository {
    fun getDetailMenu(id: String): Flowable<Resource<Menu?>>
}