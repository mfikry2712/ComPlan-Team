package com.example.complan.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.complan.R
import com.example.complan.authentication.InputProfileActivity
import com.example.complan.authentication.LoginActivity
import com.example.complan.menuandpager.Menu
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var authReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen_layout)

        auth = Firebase.auth

        val firebaseUser = auth.currentUser

        authReference = FirebaseDatabase.getInstance().getReference("user")

        Handler(Looper.getMainLooper()).postDelayed({
            if (firebaseUser == null) {
                // Not signed in, launch the Login activity
                startActivity(Intent(this, LoginActivity::class.java))
            } else {
                updateUI(firebaseUser)
            }
            finish()
        },2000)

    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null){
            authReference.child(currentUser.uid).get().addOnSuccessListener{
                if (it.exists()){
                    val o = Intent(this@MainActivity,  Menu::class.java)
                    startActivity(o)
                }else{
                    val o = Intent(this@MainActivity,  InputProfileActivity::class.java)
                    startActivity(o)
                }
            }
        }
    }
}