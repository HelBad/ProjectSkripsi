package com.example.projectskripsi.features.auth.data.responses

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("id_user")
    var id_user: String?,
    @SerializedName("nama")
    var nama: String?,
    @SerializedName("email")
    var email: String?,
    @SerializedName("password")
    var password: String?,
    @SerializedName("tgl_lahir")
    var tgl_lahir: String?,
    @SerializedName("gender")
    var gender: String?,
    @SerializedName("alamat")
    var alamat: String?,
    @SerializedName("telp")
    var telp: String?,
    @SerializedName("level")
    var level: String?,
)