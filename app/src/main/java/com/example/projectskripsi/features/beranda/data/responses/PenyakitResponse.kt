package com.example.projectskripsi.features.beranda.data.responses


import com.google.gson.annotations.SerializedName

data class PenyakitResponse(
    @SerializedName("id_menu")
    var idMenu: String?,

    @SerializedName("obesitas")
    var obesitas: String?,

    @SerializedName("diabetes")
    var diabetes: String?,

    @SerializedName("sehat")
    var sehat: String?,

    @SerializedName("anemia")
    var anemia: String?,

    @SerializedName("id_penyakit")
    var idPenyakit: String?
)