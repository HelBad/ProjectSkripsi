package com.example.projectskripsi.features.beranda.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.UseCase
import com.example.projectskripsi.features.beranda.domain.entities.Menu
import com.example.projectskripsi.features.beranda.domain.entities.Penyakit
import com.example.projectskripsi.features.beranda.domain.usecases.GetMenuUsecase
import com.example.projectskripsi.features.beranda.domain.usecases.GetPenyakitUsecase

class BerandaViewModel constructor(
    private val getMenuUsecase: GetMenuUsecase,
    private val getPenyakitUsecase: GetPenyakitUsecase,
) : ViewModel() {
    fun getMenu() : LiveData<Resource<ArrayList<Menu>>> {
        return LiveDataReactiveStreams.fromPublisher(
            getMenuUsecase.run(UseCase.NoParams())
        )
    }

    fun getPenyakit() : LiveData<Resource<ArrayList<Penyakit>>> {
        return LiveDataReactiveStreams.fromPublisher(
            getPenyakitUsecase.run(UseCase.NoParams())
        )
    }
}