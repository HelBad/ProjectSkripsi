package com.example.projectskripsi.features.riwayat.data.responses

import com.google.gson.annotations.SerializedName

data class PesananResponse(
    @SerializedName("id_pesanan")
    var id_pesanan: String?,
    @SerializedName("id_user")
    var id_user: String?,
    @SerializedName("id_keranjang")
    var id_keranjang: String?,
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
    var total_bayar: String?,
    @SerializedName("status")
    var status: String?,
    @SerializedName("keterangan")
    var keterangan: String?
)