package com.example.projectskripsi.modules.detail.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.modules.detail.domain.entities.Menu
import com.example.projectskripsi.modules.detail.domain.usecases.DetailUsecase

class DetailViewModel constructor(private val usecase: DetailUsecase) : ViewModel() {
    fun getDetailMenu(id: String) : LiveData<Resource<Menu?>> {
        return LiveDataReactiveStreams.fromPublisher(usecase.getDetailMenu(id))
    }
}