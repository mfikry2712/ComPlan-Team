package com.example.complan.authentication

import com.example.complan.dataclass.UserProfile
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.example.complan.splashscreen.MainActivity
import com.example.complan.R
import com.example.complan.databinding.ActivityInputProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class InputProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInputProfileBinding
    private lateinit var namaUser : EditText
    private lateinit var jurusanUser : EditText
    private lateinit var kodeSekolah : EditText

    private lateinit var auth: FirebaseAuth
    private lateinit var db: DatabaseReference
    private lateinit var dbi: FirebaseDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        namaUser = findViewById(R.id.edtName)
        jurusanUser = findViewById(R.id.edtJurusan)
        kodeSekolah = findViewById(R.id.edtCode)

        auth = Firebase.auth
        val firebaseUser = auth.currentUser
        if (firebaseUser == null) {
            // Not signed in, launch the Login activity
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        dbi = Firebase.database


        binding.btnSend.setOnClickListener{

            val dataSiswa = UserProfile(
                namaUser.text.toString(),
                jurusanUser.text.toString(),
                kodeSekolah.text.toString()
            )

            dbi.reference.child(CHILD_USER).child(firebaseUser.uid).setValue(dataSiswa){ error, _ ->
                if (error != null) {
                    Toast.makeText(this, "gagal" + error.message, Toast.LENGTH_SHORT).show()
                } else {
                    dbi.reference.child(CHILD_SEKOLAH).child(kodeSekolah.text.toString()).child(
                        CHILD_USER
                    )
                        .child(firebaseUser.uid).setValue(dataSiswa) { error, _ ->
                        if (error != null) {
                            Toast.makeText(this, "gagal input" + error.message, Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "berhasil", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@InputProfileActivity, MainActivity::class.java))
                            finish()
                        }
                    }
                }
            }
        }
    }

    companion object{
        const val CHILD_USER = "user"
        const val CHILD_SEKOLAH = "kode_sekolah"
    }
}