package com.example.projectskripsi.modules.beranda.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.modules.beranda.data.models.Menu
import com.example.projectskripsi.modules.beranda.data.models.Penyakit
import com.example.projectskripsi.modules.beranda.domain.usecases.BerandaUsecase

class BerandaViewModel constructor(private val usecase: BerandaUsecase) : ViewModel() {
    fun getMenu() : LiveData<Resource<ArrayList<Menu>>> {
        return LiveDataReactiveStreams.fromPublisher(usecase.getMenu())
    }

    fun getPenyakit() : LiveData<Resource<ArrayList<Penyakit>>> {
        return LiveDataReactiveStreams.fromPublisher(usecase.getPenyakit())
    }
}