package com.example.complan.inputform

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.example.complan.menuandpager.Menu
import com.example.complan.authentication.LoginActivity
import com.example.complan.databinding.ActivityLaporanPersonBinding
import com.example.complan.dataclass.DataLaporanPerson
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import java.util.*

class LaporanPersonActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLaporanPersonBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: DatabaseReference
    private lateinit var dbi: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLaporanPersonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        val firebaseUser = auth.currentUser
        if (firebaseUser == null) {
            // Not signed in, launch the Login activity
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        db = FirebaseDatabase.getInstance().getReference("kode_sekolah")
        dbi = FirebaseDatabase.getInstance().getReference("user").child(firebaseUser.uid)

        var testName = false
        var testLocation = false
        var testDesc = false


        binding.edtOknumName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                testName = s.isNotEmpty()
                binding.btnSendPerson.isEnabled = testName && testLocation && testDesc
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        binding.edtCategory.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                testLocation = s.isNotEmpty()
                binding.btnSendPerson.isEnabled = testName && testLocation && testDesc
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        binding.edtDescription.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                testDesc = s.isNotEmpty()
                binding.btnSendPerson.isEnabled = testName && testLocation && testDesc
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        binding.btnSendPerson.setOnClickListener {
            val friendlyMessage = DataLaporanPerson(
                binding.edtOknumName.text.toString(),
                binding.edtCategory.text.toString(),
                binding.edtDescription.text.toString(),
                Date().time
            )
            dbi.get().addOnSuccessListener{
               val kdSekolah =  it.child("schoolCode").value
                db.child(kdSekolah.toString()).child("Laporan").child(firebaseUser.uid).child("Laporan Orang").push().setValue(friendlyMessage) { error, _ ->
                    if (error != null) {
                        Toast.makeText(this, "Error" + error.message, Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                    }
                }
            }

           // binding.editTextTextPersonName.setText("")
        }

        binding.btnBack.setOnClickListener{
            startActivity(Intent(this@LaporanPersonActivity, Menu::class.java))
        }

    }
}