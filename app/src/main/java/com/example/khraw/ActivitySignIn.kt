package com.example.khraw

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.khraw.Ui.Home.FragmentProfile


class ActivitySignIn : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")

    private lateinit var dbHelper: DBHelper

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_in)

        dbHelper = DBHelper(this)

        val btnSignIn = findViewById<Button>(R.id.button_signIn)
        val etEmail = findViewById<EditText>(R.id.edit_signIn_email)
        val etPassword =
            findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.edit_signIn_password)

        btnSignIn.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

                if (email.isNotEmpty() && password.isNotEmpty()) {
                    val isAuthenticated = dbHelper.authenticateUser(email, password)
                    if (isAuthenticated) {

                        saveLoggedInEmail(email)
                        val sharedPref = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
                        with(sharedPref.edit()) {
                            putBoolean("isSignedIn", true)
                            apply()
                        }
                        val intent = Intent(this, ActivityMenu::class.java)
                        startActivity(intent)
                        Toast.makeText(this, "SignIn Successful", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                }
            }


            val singUp = findViewById<TextView>(R.id.text_signUp).setOnClickListener {
                val intent = Intent(this, ActivityRegister::class.java)
                startActivity(intent)
            }

            val forget = findViewById<TextView>(R.id.text_forgetPassword).setOnClickListener {
                //ฟังชันเวลาลืมรหัสผ่าน
            }
        }
    private fun saveLoggedInEmail(email: String) {
        val sharedPref = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("loggedInEmail", email)
            apply()
        }
    }
}