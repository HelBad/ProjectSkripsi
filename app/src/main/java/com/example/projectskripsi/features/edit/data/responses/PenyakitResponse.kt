package com.example.projectskripsi.features.edit.data.responses

import com.google.gson.annotations.SerializedName

data class PenyakitResponse(
    @SerializedName("id_penyakit")
    var id_penyakit: String?,
    @SerializedName("id_menu")
    var id_menu: String?,
    @SerializedName("sehat")
    var sehat: String?,
    @SerializedName("diabetes")
    var diabetes: String?,
    @SerializedName("jantung")
    var jantung: String?,
    @SerializedName("kelelahan")
    var kelelahan: String?,
    @SerializedName("obesitas")
    var obesitas: String?,
    @SerializedName("sembelit")
    var sembelit: String?
)