package com.example.projectskripsi.features.edit.di

import com.example.projectskripsi.features.edit.data.repositories.EditRepositoryImpl
import com.example.projectskripsi.features.edit.data.source.remote.EditRemoteDataSource
import com.example.projectskripsi.features.edit.domain.repositories.EditRepository
import com.example.projectskripsi.features.edit.domain.usecases.*
import com.example.projectskripsi.features.edit.presentation.viewmodel.EditViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val editModule = module {
    factory { EditRemoteDataSource() }

    single<EditRepository> { EditRepositoryImpl(get()) }

    single { GetDetailMenuUsecase(get()) }
    single { BuatMenuUsecase(get()) }
    single { UpdateMenuUsecase(get()) }
    single { GetDetailPenyakitUsecase(get()) }
    single { BuatPenyakitUsecase(get()) }
    single { UpdatePenyakitUsecase(get()) }
    single { UploadGambarUsecase(get()) }

    viewModel { EditViewModel(get(), get(), get(), get(), get(), get(), get()) }
}