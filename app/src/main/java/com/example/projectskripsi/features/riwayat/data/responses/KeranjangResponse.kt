package com.example.projectskripsi.features.riwayat.data.responses


import com.google.gson.annotations.SerializedName

data class KeranjangResponse(
    @SerializedName("id_keranjang")
    var idKeranjang: String?,
    @SerializedName("total")
    var total: String?,
    @SerializedName("id_menu")
    var idMenu: String?,
    @SerializedName("jumlah")
    var jumlah: String?,
    @SerializedName("id_user")
    var idUser: String?
)