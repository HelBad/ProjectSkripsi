package com.example.projectskripsi.modules.profil.presentation.fragment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.projectskripsi.R
import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.modules.auth.presentation.ActivityLogin
import com.example.projectskripsi.modules.profil.domain.entities.User
import com.example.projectskripsi.modules.profil.presentation.viewmodel.ProfilViewModel
import com.example.projectskripsi.utils.Tanggal
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class FragmentProfilUser : Fragment() {
    private val profilViewModel: ProfilViewModel by viewModel()

    private lateinit var alertDialog: AlertDialog.Builder
    private lateinit var profilNama: EditText
    private lateinit var profilEmail: EditText
    private lateinit var profilPassword: EditText
    private lateinit var profilTanggal: TextView
    private lateinit var profilSpinner: Spinner
    private lateinit var profilGender: TextView
    private lateinit var profilAlamat: EditText
    private lateinit var profilTelp: EditText
    private lateinit var btnSimpan: Button
    private lateinit var btnKeluar: Button

    var user: User? = null

    @SuppressLint("NewApi")
    val date : Calendar = Calendar.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profil, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profilNama = requireActivity().findViewById(R.id.profilNama)
        profilEmail = requireActivity().findViewById(R.id.profilEmail)
        profilPassword = requireActivity().findViewById(R.id.profilPassword)
        profilTanggal = requireActivity().findViewById(R.id.profilTanggal)
        profilSpinner = requireActivity().findViewById(R.id.profilSpinner)
        profilGender = requireActivity().findViewById(R.id.profilGender)
        profilAlamat = requireActivity().findViewById(R.id.profilAlamat)
        profilTelp = requireActivity().findViewById(R.id.profilTelp)
        btnSimpan = requireActivity().findViewById(R.id.btnSimpan)
        btnKeluar = requireActivity().findViewById(R.id.btnKeluar)
        alertDialog = AlertDialog.Builder(requireActivity())

        profilTanggal.setOnClickListener {
            setTanggal()
        }
        jenisKelamin()
        loadData()

        btnKeluar.setOnClickListener {
            alertDialog.setTitle("Keluar Akun")
            alertDialog.setMessage("Apakah anda ingin keluar dari akun ini ?").setCancelable(false)
                .setPositiveButton("YA") { _, _ ->
                    profilViewModel.saveUser("", "", "", "", "", "", "", "", "")
                        .observe(viewLifecycleOwner) { res ->
                            if (res is Resource.Success) {
                                val intent = Intent(context, ActivityLogin::class.java)
                                startActivity(intent)
                                requireActivity().finish()
                            } else if (res is Resource.Error) {
                                Toast.makeText(
                                    requireActivity(),
                                    "Terjadi kesalahan. Silahkan coba lagi",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                }
                .setNegativeButton("TIDAK") { dialog, _ -> dialog.cancel() }.create().show()
        }

        btnSimpan.setOnClickListener {
            alertDialog.setMessage("Apakah ingin menyimpan perubahan data ?").setCancelable(false)
                .setPositiveButton("YA") { _, _ ->
                    if (validate()) {
                        saveData()
                    }
                }
                .setNegativeButton("TIDAK") { dialog, _ -> dialog.cancel() }.create().show()
        }
    }

    private fun loadData() {
        profilViewModel.getUser().observe(viewLifecycleOwner) {
            if (it is Resource.Success) {
                user = it.data
                profilNama.setText(it.data?.nama)
                profilEmail.setText(it.data?.email)
                profilPassword.setText(it.data?.password)
                profilTanggal.text = it.data?.tglLahir
                profilGender.text = it.data?.gender
                profilAlamat.setText(it.data?.alamat)
                profilTelp.setText(it.data?.telp)
            }
        }
    }

    //Set Tanggal
    private fun setTanggal() {
        val date = DatePickerDialog(
            requireActivity(),
            { _, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(Calendar.YEAR, year)
                selectedDate.set(Calendar.MONTH, month)
                selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                profilTanggal.text = Tanggal.format(selectedDate.time)
            },
            date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH)
        )
        date.show()
    }

    //Pilih Jenis Kelamin
    private fun jenisKelamin() {
        val genderUser = arrayOf("Laki-laki", "Perempuan")
        profilSpinner.adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_list_item_1, genderUser)
        profilSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                profilGender.text = "Masukkan Jenis Kelamin"
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                profilGender.text = genderUser[position]
            }
        }
    }

    //Validasi Data Akun
    private fun validate(): Boolean {
        if(profilNama.text.toString() == "") {
            Toast.makeText(activity, "Nama masih kosong", Toast.LENGTH_SHORT).show()
            return false
        }
        if(profilEmail.text.toString() == "") {
            Toast.makeText(activity, "Email masih kosong", Toast.LENGTH_SHORT).show()
            return false
        }
        if(profilPassword.text.toString() == "") {
            Toast.makeText(activity, "Password masih kosong", Toast.LENGTH_SHORT).show()
            return false
        }
        if(profilTanggal.text.toString() == "") {
            Toast.makeText(activity, "Tanggal lahir kosong", Toast.LENGTH_SHORT).show()
            return false
        }
        if(profilAlamat.text.toString() == "") {
            Toast.makeText(activity, "Alamat masih kosong", Toast.LENGTH_SHORT).show()
            return false
        }
        if(profilTelp.text.toString() == "") {
            Toast.makeText(activity, "Nomor telepon kosong", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    //Simpan Data Akun
    private fun saveData() {
        if (user != null) {
            profilViewModel.updateUser(
                user?.idUser ?: "",
                user?.nama ?: "",
                user?.email ?: "",
                user?.password ?: "",
                user?.tglLahir ?: "",
                user?.gender ?: "",
                user?.alamat ?: "",
                user?.telp ?: "",
                user?.level ?: ""
            ).observe(viewLifecycleOwner) { res ->
                if (res is Resource.Success) {
                    profilViewModel.saveUser(user?.idUser, user?.nama, user?.email, user?.password, user?.tglLahir, user?.gender, user?.alamat, user?.telp, user?.level)
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