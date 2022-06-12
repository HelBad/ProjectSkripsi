package com.example.projectskripsi.features.profil.presentation.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.projectskripsi.R
import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.features.auth.presentation.ActivityLogin
import com.example.projectskripsi.features.profil.domain.entities.User
import com.example.projectskripsi.features.profil.presentation.viewmodel.ProfilViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class FragmentProfilAdmin : Fragment() {
    private val profilViewModel: ProfilViewModel by viewModel()
    private lateinit var alertDialog: AlertDialog.Builder
    private lateinit var profilEmail: EditText
    private lateinit var profilPassword: EditText
    private lateinit var btnSimpan: Button
    private lateinit var btnKeluar: Button

    var user: User? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.admin_fragment_profil, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profilEmail = requireActivity().findViewById(R.id.profilEmail)
        profilPassword = requireActivity().findViewById(R.id.profilPassword)
        btnSimpan = requireActivity().findViewById(R.id.btnSimpan)
        btnKeluar = requireActivity().findViewById(R.id.btnKeluar)

        alertDialog = AlertDialog.Builder(requireActivity())
        loadData()

        btnKeluar.setOnClickListener {
            alertDialog.setTitle("Keluar Akun")
            alertDialog.setMessage("Apakah anda ingin keluar dari akun ini ?").setCancelable(false)
                .setPositiveButton("YA"
                ) { _, _ ->
                    profilViewModel.saveUser("", "", "", "", "", "", "", "", "")
                        .observe(viewLifecycleOwner) { res ->
                            if (res is Resource.Success) {
                                val intent = Intent(context, ActivityLogin::class.java)
                                startActivity(intent)
                                requireActivity().finish()
                            } else if (res is Resource.Error) {
                                Toast.makeText(requireActivity(), "Terjadi kesalahan. Silahkan coba lagi", Toast.LENGTH_SHORT).show()
                            }
                        }
                }
                .setNegativeButton("TIDAK"
                ) { dialog, _ -> dialog.cancel() }.create().show()
        }

        btnSimpan.setOnClickListener {
            alertDialog.setMessage("Apakah ingin menyimpan perubahan data ?").setCancelable(false)
                .setPositiveButton("YA"
                ) { _, _ ->
                    if (validate()) {
                        saveData()
                    }
                }
                .setNegativeButton("TIDAK"
                ) { dialog, _ -> dialog.cancel() }.create().show()
        }
    }

    private fun loadData() {
        profilViewModel.getUser().observe(viewLifecycleOwner) {
            if (it != null) {
                user = it.data
                profilEmail.setText(user?.email ?: "")
                profilPassword.setText(user?.password ?: "")
            }
        }
    }

    //Validasi Data User
    private fun validate(): Boolean {
        if(profilEmail.text.toString() == "") {
            Toast.makeText(activity, "Email masih kosong", Toast.LENGTH_SHORT).show()
            return false
        }
        if(profilPassword.text.toString() == "") {
            Toast.makeText(activity, "Password masih kosong", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    //Simpan Perubahan Data User
    private fun saveData() {
        if (user != null) {
            profilViewModel.updateUser(
                user?.id_user ?: "",
                user?.nama ?: "",
                user?.email ?: "",
                user?.password ?: "",
                user?.tgl_lahir ?: "",
                user?.gender ?: "",
                user?.alamat ?: "",
                user?.telp ?: "",
                user?.level ?: ""
            ).observe(viewLifecycleOwner) { res ->
                    if (res is Resource.Success) {
                        profilViewModel.saveUser(user?.id_user, user?.nama, user?.email, user?.password, user?.tgl_lahir, user?.gender, user?.alamat, user?.telp, user?.level)
                            .observe(viewLifecycleOwner) { res1 ->
                                if (res1 is Resource.Success) {
                                    Toast.makeText(activity, "Data berhasil disimpan", Toast.LENGTH_SHORT).show()
                                } else if (res1 is Resource.Error) {
                                    Toast.makeText(requireActivity(), "Terjadi kesalahan. Silahkan coba lagi", Toast.LENGTH_SHORT).show()
                                }
                            }
                    } else if (res is Resource.Error) {
                        Toast.makeText(requireActivity(), "Terjadi kesalahan. Silahkan coba lagi", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}