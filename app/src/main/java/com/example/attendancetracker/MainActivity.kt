package com.example.attendancetracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.TextClock
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var digitalClock: TextClock
    private val handler = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        digitalClock = findViewById(R.id.digitalClock)

        // Start updating the clock
        handler.post(updateClockRunnable)
//        val btnLogout = findViewById<Button>(R.id.btnLogout)
//        btnLogout.setOnClickListener{
//
//            Firebase.auth.signOut()
//
//            val intent = Intent(this, Login::class.java)
//            startActivity(intent)
//            finish()
//        }
    }
    private val updateClockRunnable = object : Runnable {
        override fun run() {
            updateClock()
            handler.postDelayed(this, 1000) // Update every 1 second
        }
    }
    private fun updateClock() {
        val timeZone = TimeZone.getTimeZone("Asia/Manila") // Replace with your desired timezone ID
        val sdf = SimpleDateFormat("MMM dd, yyyy hh:mm:ss a", Locale.getDefault())
        sdf.timeZone = timeZone

        val currentTime = sdf.format(Date())
        digitalClock.text = currentTime
    }
    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(updateClockRunnable) // Stop updating when activity is destroyed
    }
}