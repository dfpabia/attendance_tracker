package com.example.attendancetracker


import androidx.appcompat.app.AppCompatActivity
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()
        val username = findViewById<EditText>(R.id.txtUsername)
        val password = findViewById<EditText>(R.id.txtPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        btnLogin.setOnClickListener{

            val email = username.text.toString()
            val pass = password.text.toString()

            if (email.isEmpty() || pass.isEmpty()) {

                Toast.makeText(this, "Incomplete Details, please check all fields", Toast.LENGTH_LONG).show()
            } else {
                auth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG,"signInWithEmail:success", )

                            Toast.makeText(this, "User successfully authenticated", Toast.LENGTH_LONG).show()
                            val user = auth.currentUser
                            val intent = Intent(this, TimeTrackerActivity::class.java)
                            intent.putExtra("data", user)
                            startActivity(intent)
                            finish()
                            //updateUI(user)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.exception)

                            Toast.makeText(this, "Filed Authentication", Toast.LENGTH_LONG).show()
                          //  updateUI(null)
                        }
                    }
            }




        }
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        btnRegister.setOnClickListener{
            val intent = Intent(this, Registration::class.java)
            startActivity(intent)
            finish()
        }
    }
    public override fun onStart() {
        super.onStart()
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