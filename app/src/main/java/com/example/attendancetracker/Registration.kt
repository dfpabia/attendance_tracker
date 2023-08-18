package com.example.attendancetracker

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Registration : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        auth = FirebaseAuth.getInstance()
        val username = findViewById<EditText>(R.id.txtEmail)
        val password = findViewById<EditText>(R.id.txtPassword)
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val progressbar = findViewById<ProgressBar>(R.id.progressBar)
        btnRegister.setOnClickListener{
            progressbar.visibility= View.VISIBLE
            val email = username.text.toString()
            val pass = password.text.toString()

            if (email.isEmpty() || pass.isEmpty()) {

                Toast.makeText(this, "Incomplete Details, please check all fields", Toast.LENGTH_SHORT).show()

            } else {
                auth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success")
                            Firebase.auth.signOut()
                            Toast.makeText(this, "Account Created: Redirecting to login page...", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, Login::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)

                            Toast.makeText(this, "Authentication Failed", Toast.LENGTH_SHORT).show()
                          //  updateUI(null)
                        }
                    }
            }

            progressbar.visibility= View.GONE
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            Toast.makeText(this, "Already SignIn: Redirecting to home page", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, TimeTrackerActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}