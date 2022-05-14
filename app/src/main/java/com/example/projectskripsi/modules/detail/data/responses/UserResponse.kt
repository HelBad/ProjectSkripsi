package com.example.projectskripsi.modules.detail.data.responses


import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("id_user")
    var idUser: String?,
    @SerializedName("nama")
    var nama: String?,
    @SerializedName("email")
    var email: String?,
    @SerializedName("password")
    var password: String?,
    @SerializedName("tgl_lahir")
    var tglLahir: String?,
    @SerializedName("gender")
    var gender: String?,
    @SerializedName("alamat")
    var alamat: String?,
    @SerializedName("telp")
    var telp: String?,
    @SerializedName("level")
    var level: String?,
)