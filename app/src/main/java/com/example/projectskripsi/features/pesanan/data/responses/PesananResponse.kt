package com.example.projectskripsi.features.pesanan.data.responses


import com.google.gson.annotations.SerializedName

data class PesananResponse(
    @SerializedName("id_pesanan")
    var idPesanan: String?,
    @SerializedName("id_user")
    var idUser: String?,
    @SerializedName("id_keranjang")
    var idKeranjang: String?,
    @SerializedName("catatan")
    var catatan: String?,
    @SerializedName("waktu")
    var waktu: String?,
    @SerializedName("lokasi")
    var lokasi: String?,
    @SerializedName("subtotal")
    var subtotal: String?,
    @SerializedName("ongkir")
    var ongkir: String?,
    @SerializedName("total_bayar")
    var totalBayar: String?,
    @SerializedName("status")
    var status: String?,
    @SerializedName("keterangan")
    var keterangan: String?
)