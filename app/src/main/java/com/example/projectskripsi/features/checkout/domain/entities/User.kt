package com.example.projectskripsi.features.checkout.domain.entities

data class User(
    var idUser: String? = null,
    var nama: String? = null,
    var email: String? = null,
    var password: String? = null,
    var tglLahir: String? = null,
    var gender: String? = null,
    var alamat: String? = null,
    var telp: String? = null,
    var level: String? = null,
)