package com.example.complan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.complan.authentication.LoginActivity
import com.example.complan.authentication.RegisterActivity
import com.example.complan.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        val firebaseUser = auth.currentUser
        if (firebaseUser == null) {
            // Not signed in, launch the Login activity
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        binding.cek.setOnClickListener {
            //Toast.makeText(this, auth.uid, Toast.LENGTH_LONG).show()
            val goToRegis = Intent(this@MainActivity, RegisterActivity::class.java)
            goToRegis.putExtra("phone","+628581234567")
            startActivity(goToRegis)
        }


    }
}