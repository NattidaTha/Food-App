package com.example.khraw

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.khraw.Ui.FragmentUi.MyPagerAdapter
import com.example.khraw.Ui.Oder.SharedViewModel
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator

class ActivityLogin : AppCompatActivity() {
    @get:SuppressLint("MissingInflatedId")

    private val sharedViewModel: SharedViewModel by viewModels()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        val viewPager: ViewPager = findViewById(R.id.view_pager)
        val dotsIndicator: DotsIndicator = findViewById(R.id.dotsIndicator)

        val adapter = MyPagerAdapter(supportFragmentManager)

        viewPager.adapter = adapter

        dotsIndicator.setViewPager(viewPager)

        val singUpButton = findViewById<Button>(R.id.btn_singUp_main)
        singUpButton.setOnClickListener {
            val intent = Intent(this, ActivityRegister::class.java)
            startActivity(intent)
        }

        val singInButton = findViewById<Button>(R.id.btn_singIn_main)
        singInButton.setOnClickListener {
            val intent = Intent(this, ActivitySignIn::class.java)
            startActivity(intent)
        }
    }
}