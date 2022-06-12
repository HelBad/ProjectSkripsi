package com.example.projectskripsi.features.checkout.data.responses

import com.google.gson.annotations.SerializedName

data class KeranjangResponse(
    @SerializedName("id_keranjang")
    var id_keranjang: String?,
    @SerializedName("total")
    var total: String?,
    @SerializedName("id_menu")
    var id_menu: String?,
    @SerializedName("jumlah")
    var jumlah: String?,
    @SerializedName("id_user")
    var id_user: String?,
    @SerializedName("nama_menu")
    var nama_menu: String?
)