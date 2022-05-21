package com.example.projectskripsi.features.checkout.data.responses


import com.google.gson.annotations.SerializedName

data class MenuResponse(
    @SerializedName("kalori")
    var kalori: String?,

    @SerializedName("karbohidrat")
    var karbohidrat: String?,

    @SerializedName("id_menu")
    var idMenu: String?,

    @SerializedName("nama_menu")
    var namaMenu: String?,

    @SerializedName("harga")
    var harga: String?,

    @SerializedName("protein")
    var protein: String?,

    @SerializedName("deskripsi")
    var deskripsi: String?,

    @SerializedName("gambar")
    var gambar: String?,

    @SerializedName("lemak")
    var lemak: String?
)