package com.example.projectskripsi.features.profil.domain.entities

data class User(
    var id_user: String? = null,
    var nama: String? = null,
    var email: String? = null,
    var password: String? = null,
    var tgl_lahir: String? = null,
    var gender: String? = null,
    var alamat: String? = null,
    var telp: String? = null,
    var level: String? = null,
)