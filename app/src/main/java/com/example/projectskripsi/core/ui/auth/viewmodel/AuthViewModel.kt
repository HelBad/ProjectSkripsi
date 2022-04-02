package com.example.projectskripsi.core.ui.auth.viewmodel

import androidx.lifecycle.ViewModel
import com.example.projectskripsi.core.domain.usecases.AuthUsecase

class AuthViewModel constructor(private val usecase: AuthUsecase) : ViewModel() {
    fun login(email: String, password: String) = usecase.login(email, password)

    fun register(nama: String, email: String, password: String, tanggal: String, gender: String, alamat: String, telp: String) =
        usecase.register(nama, email, password, tanggal, gender, alamat, telp)
}