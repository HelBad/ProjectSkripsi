package com.example.projectskripsi.features.menu.data.responses

import com.google.gson.annotations.SerializedName

data class KeranjangResponse(
    @SerializedName("id_keranjang")
    var id_keranjang: String?,
    @SerializedName("id_user")
    var id_user: String?,
    @SerializedName("id_menu")
    var id_menu: String?,
    @SerializedName("jumlah")
    var jumlah: String?,
    @SerializedName("total")
    var total: String?,
)