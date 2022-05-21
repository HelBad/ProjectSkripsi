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

    fun getDetailMenu(id: String): Flowable<Response<MenuResponse?>> {
        val response = PublishSubject.create<Response<MenuResponse?>>()

        firebase.getReference("menu").orderByKey().equalTo(id)
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

    fun getDetailPenyakit(idMenu: String): Flowable<Response<PenyakitResponse?>> {
        val response = PublishSubject.create<Response<PenyakitResponse?>>()

        firebase.getReference("penyakit").orderByChild("id_menu").equalTo(idMenu)
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
        nama: String,
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
        val id = ref.push().key.toString()
        val data = MenuResponse(
            id_menu = id,
            nama_menu = nama,
            deskripsi = deskripsi,
            lemak = lemak,
            protein = protein,
            kalori = kalori,
            karbohidrat = karbohidrat,
            harga = harga,
            gambar = gambar
        )
        ref.child(id).setValue(data)
            .addOnCompleteListener {
                response.onNext(Response.Success(id))
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
        id: String,
        nama: String,
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
            id_menu = id,
            nama_menu = nama,
            deskripsi = deskripsi,
            lemak = lemak,
            protein = protein,
            kalori = kalori,
            karbohidrat = karbohidrat,
            harga = harga,
            gambar = gambar
        )
        firebase.getReference("menu").child(id).setValue(data)
            .addOnCompleteListener {
                response.onNext(Response.Success(id))
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
        idMenu: String,
        sehat: String,
        diabetes: String,
        obesitas: String,
        anemia: String,
    ): Flowable<Response<String?>> {
        val response = PublishSubject.create<Response<String?>>()

        val ref = firebase.getReference("penyakit")
        val id = ref.push().key.toString()
        val data = PenyakitResponse(
            id_penyakit = id,
            id_menu = idMenu,
            sehat = sehat,
            diabetes = diabetes,
            obesitas = obesitas,
            anemia = anemia,
        )
        ref.child(id).setValue(data)
            .addOnCompleteListener {
                response.onNext(Response.Success(id))
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
        id: String,
        idMenu: String,
        sehat: String,
        diabetes: String,
        obesitas: String,
        anemia: String,
    ): Flowable<Response<String?>> {
        val response = PublishSubject.create<Response<String?>>()

        val data = PenyakitResponse(
            id_penyakit = id,
            id_menu = idMenu,
            sehat = sehat,
            diabetes = diabetes,
            obesitas = obesitas,
            anemia = anemia,
        )
        firebase.getReference("penyakit").child(id).setValue(data)
            .addOnCompleteListener {
                response.onNext(Response.Success(id))
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
        idMenu: String,
        uri: Uri,
    ): Flowable<Response<Uri?>> {
        val response = PublishSubject.create<Response<Uri?>>()

        val ref = storage.getReference("menu").child(idMenu)
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