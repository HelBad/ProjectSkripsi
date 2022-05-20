package com.example.projectskripsi.features.profil.di

import com.example.projectskripsi.features.profil.data.repositories.ProfilRepositoryImpl
import com.example.projectskripsi.features.profil.data.source.local.ProfilLocalDataSource
import com.example.projectskripsi.features.profil.data.source.remote.ProfilRemoteDataSource
import com.example.projectskripsi.features.profil.domain.repositories.ProfilRepository
import com.example.projectskripsi.features.profil.domain.usecases.GetUserUsecase
import com.example.projectskripsi.features.profil.domain.usecases.SaveUserUsecase
import com.example.projectskripsi.features.profil.domain.usecases.UpdateUserUsecase
import com.example.projectskripsi.features.profil.presentation.viewmodel.ProfilViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val profilModule = module {
    factory { ProfilRemoteDataSource() }

    factory { ProfilLocalDataSource() }

    single<ProfilRepository> { ProfilRepositoryImpl(get(), get()) }

    single { GetUserUsecase(get()) }
    single { SaveUserUsecase(get()) }
    single { UpdateUserUsecase(get()) }

    viewModel { ProfilViewModel(get(), get(), get()) }
}