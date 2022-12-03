package com.example.complan.inputform

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        dbi = FirebaseDatabase.getInstance().getReference("user").child(firebaseUser.uid.toString())

        //val messagesRef = db.reference.child("")
        //menambah comment

        binding.button.setOnClickListener {
            val friendlyMessage = DataLaporanPerson(
                binding.edtOknumName.text.toString(),
                binding.edtCategory.text.toString(),
                binding.edtDescription.text.toString(),
                Date().time
            )
            dbi.get().addOnSuccessListener{
               val kdSekolah =  it.child("kodeSekolah").value
                db.child(kdSekolah.toString()).child("Laporan").child(firebaseUser.uid).child("Laporan Orang").push().setValue(friendlyMessage) { error, _ ->
                    if (error != null) {
                        Toast.makeText(this, "gagal" + error.message, Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "berhasil", Toast.LENGTH_SHORT).show()
                    }
                }
            }

           // binding.editTextTextPersonName.setText("")
        }

        binding.btnBack.setOnClickListener{
            startActivity(Intent(this@LaporanPersonActivity, Menu::class.java))
        }

    }

    companion object{
        const val CHILD_DATA = "oknum"
    }
}