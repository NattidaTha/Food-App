package com.example.khraw

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class ActivityRegister : AppCompatActivity() {

    private lateinit var dbHelper: DBHelper

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        dbHelper = DBHelper(this)

        val etName = findViewById<EditText>(R.id.edit_singUp_name)
        val etEmail = findViewById<EditText>(R.id.edit_singUp_email)
        val etPassword = findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.edit_signUp_password)
        val btnSignUp = findViewById<Button>(R.id.button_Signup)

        val singIn = findViewById<TextView>(R.id.text_singIn).setOnClickListener {
            val intent = Intent(this, ActivitySignIn::class.java)
            startActivity(intent)
        }

        btnSignUp.setOnClickListener {
            val name = etName.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()


            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                if (!isEmailValid(email)) {
                   showToast("Invalid email format")
                    return@setOnClickListener
                }
                if (dbHelper.checkUser(name)) {
                    showToast("Username already exists")
                    return@setOnClickListener
                }
                if (dbHelper.checkEmail(email)) {
                    showToast("Email already exists")
                } else {
                    dbHelper.addUser(name, email, password)
                    showToast("SignUp Successful")
                        val intent = Intent(this, ActivitySignIn::class.java)
                        startActivity(intent)
                }
            } else {
                showToast("Please fill in all fields")
            }
        }
    }
    private fun isEmailValid(email: String): Boolean { //ฟอแมท
        val emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\$".toRegex()
        return emailRegex.matches(email)
    }
    private fun showToast (msg: CharSequence){
        Toast.makeText (this@ActivityRegister ,msg , Toast.LENGTH_LONG).show()
    }
}