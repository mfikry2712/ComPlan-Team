package com.example.complan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.complan.authentication.LoginActivity
import com.example.complan.databinding.ActivityLaporanBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*

class LaporanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLaporanBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: DatabaseReference
    private lateinit var dbi: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLaporanBinding.inflate(layoutInflater)
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
        dbi = FirebaseDatabase.getInstance().getReference("user").child(firebaseUser.uid.toString())

        //val messagesRef = db.reference.child("")
        //menambah comment

        binding.button.setOnClickListener {
            val friendlyMessage = DataLaporan(
                firebaseUser.uid,
                binding.edtOknumName.text.toString(),
                binding.edtDescription.text.toString(),
                Date().time
            )
            dbi.get().addOnSuccessListener{
               val kdSekolah =  it.child("kodeSekolah").value
                db.child(kdSekolah.toString()).child("Laporan").push().setValue(friendlyMessage) { error, _ ->
                    if (error != null) {
                        Toast.makeText(this, "gagal" + error.message, Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "berhasil", Toast.LENGTH_SHORT).show()
                    }
                }
            }

           // binding.editTextTextPersonName.setText("")
        }

    }

    companion object{
        const val CHILD_DATA = "oknum"
    }
}