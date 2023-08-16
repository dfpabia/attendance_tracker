package com.example.attendancetracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnLogout = findViewById<Button>(R.id.btnLogout)
        btnLogout.setOnClickListener{

            Firebase.auth.signOut()

            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }
    }
}