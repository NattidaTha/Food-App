package com.example.khraw

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.khraw.Ui.Home.FragmentHome
import com.example.khraw.Ui.Oder.SharedViewModel

class ActivityMain : AppCompatActivity() {
    @get:SuppressLint("MissingInflatedId")

    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        val sharedPref = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val isSignedIn = sharedPref.getBoolean("isSignedIn", false)

        if (isSignedIn) {
            val intent = Intent(this, ActivityMenu::class.java)
            startActivity(intent)
        } else {
            val intent = Intent(this, ActivityLogin::class.java)
            startActivity(intent)
        }
    }
}