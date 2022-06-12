package com.example.projectskripsi.features.edit.data.source.remote

import android.net.Uri
import android.util.Log
import com.example.projectskripsi.core.Response
import com.example.projectskripsi.features.edit.data.responses.MenuResponse
import com.example.projectskripsi.features.edit.data.responses.PenyakitResponse
import com.example.projectskripsi.utils.Converter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject

class EditRemoteDataSource {
    private val firebase = FirebaseDatabase.getInstance()
    private val storage = FirebaseStorage.getInstance()

    fun getDetailMenu(id_menu: String): Flowable<Response<MenuResponse?>> {
        val response = PublishSubject.create<Response<MenuResponse?>>()

        firebase.getReference("menu").orderByKey().equalTo(id_menu)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (snap in snapshot.children) {
                            val menu = Converter.toObject(snap, MenuResponse::class.java)
                            response.onNext(Response.Success(menu))
                        }
                    } else {
                        response.onNext(Response.Empty)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    response.onNext(Response.Error(error.message))
                    Log.e("DetailRemoteDataSource", error.message)
                }
            })
        return response.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun getDetailPenyakit(id_menu: String): Flowable<Response<PenyakitResponse?>> {
        val response = PublishSubject.create<Response<PenyakitResponse?>>()

        firebase.getReference("penyakit").orderByChild("id_menu").equalTo(id_menu)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (snap in snapshot.children) {
                            val menu = Converter.toObject(snap, PenyakitResponse::class.java)
                            response.onNext(Response.Success(menu))
                        }
                    } else {
                        response.onNext(Response.Empty)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    response.onNext(Response.Error(error.message))
                    Log.e("DetailRemoteDataSource", error.message)
                }
            })
        return response.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun buatMenu(
        nama_menu: String,
        deskripsi: String,
        lemak: String,
        protein: String,
        kalori: String,
        karbohidrat: String,
        harga: String,
        gambar: String
    ): Flowable<Response<String?>> {
        val response = PublishSubject.create<Response<String?>>()

        val ref = firebase.getReference("menu")
        val id_menu = ref.push().key.toString()
        val data = MenuResponse(
            id_menu = id_menu,
            nama_menu = nama_menu,
            deskripsi = deskripsi,
            lemak = lemak,
            protein = protein,
            kalori = kalori,
            karbohidrat = karbohidrat,
            harga = harga,
            gambar = gambar
        )
        ref.child(id_menu).setValue(data)
            .addOnCompleteListener {
                response.onNext(Response.Success(id_menu))
            }
            .addOnCanceledListener {
                response.onNext(Response.Error("error"))
            }
            .addOnFailureListener {
                response.onNext(Response.Error(it.message.toString()))
            }
        return response.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun updateMenu(
        id_menu: String,
        nama_menu: String,
        deskripsi: String,
        lemak: String,
        protein: String,
        kalori: String,
        karbohidrat: String,
        harga: String,
        gambar: String
    ): Flowable<Response<String?>> {
        val response = PublishSubject.create<Response<String?>>()

        val data = MenuResponse(
            id_menu = id_menu,
            nama_menu = nama_menu,
            deskripsi = deskripsi,
            lemak = lemak,
            protein = protein,
            kalori = kalori,
            karbohidrat = karbohidrat,
            harga = harga,
            gambar = gambar
        )
        firebase.getReference("menu").child(id_menu).setValue(data)
            .addOnCompleteListener {
                response.onNext(Response.Success(id_menu))
            }
            .addOnCanceledListener {
                response.onNext(Response.Error("error"))
            }
            .addOnFailureListener {
                response.onNext(Response.Error(it.message.toString()))
            }
        return response.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun buatPenyakit(
        id_menu: String,
        sehat: String,
        diabetes: String,
        jantung: String,
        kelelahan: String,
        obesitas: String,
        sembelit: String
    ): Flowable<Response<String?>> {
        val response = PublishSubject.create<Response<String?>>()

        val ref = firebase.getReference("penyakit")
        val id_penyakit = ref.push().key.toString()
        val data = PenyakitResponse(
            id_penyakit = id_penyakit,
            id_menu = id_menu,
            sehat = sehat,
            diabetes = diabetes,
            jantung = jantung,
            kelelahan = kelelahan,
            obesitas = obesitas,
            sembelit = sembelit
        )
        ref.child(id_penyakit).setValue(data)
            .addOnCompleteListener {
                response.onNext(Response.Success(id_penyakit))
            }
            .addOnCanceledListener {
                response.onNext(Response.Error("error"))
            }
            .addOnFailureListener {
                response.onNext(Response.Error(it.message.toString()))
            }
        return response.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun updatePenyakit(
        id_penyakit: String,
        id_menu: String,
        sehat: String,
        diabetes: String,
        jantung: String,
        kelelahan: String,
        obesitas: String,
        sembelit: String,
    ): Flowable<Response<String?>> {
        val response = PublishSubject.create<Response<String?>>()

        val data = PenyakitResponse(
            id_penyakit = id_penyakit,
            id_menu = id_menu,
            sehat = sehat,
            diabetes = diabetes,
            jantung = jantung,
            kelelahan = kelelahan,
            obesitas = obesitas,
            sembelit = sembelit
        )
        firebase.getReference("penyakit").child(id_penyakit).setValue(data)
            .addOnCompleteListener {
                response.onNext(Response.Success(id_penyakit))
            }
            .addOnCanceledListener {
                response.onNext(Response.Error("error"))
            }
            .addOnFailureListener {
                response.onNext(Response.Error(it.message.toString()))
            }
        return response.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun uploadGambar(
        id_menu: String,
        uri: Uri,
    ): Flowable<Response<Uri?>> {
        val response = PublishSubject.create<Response<Uri?>>()

        val ref = storage.getReference("menu").child(id_menu)
        try {
            ref.putFile(uri)
                .addOnSuccessListener {
                    ref.downloadUrl.addOnCompleteListener { taskSnapshot ->
                        response.onNext(Response.Success(taskSnapshot.result))
                    }
                }
                .addOnCanceledListener {
                    response.onNext(Response.Error("error"))
                }
                .addOnFailureListener {
                    response.onNext(Response.Error(it.message.toString()))
                }
        } catch (ex: Exception) {
            response.onNext(Response.Error(ex.message.toString()))
        }
        return response.toFlowable(BackpressureStrategy.BUFFER)
    }
}