package com.example.khraw

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.khraw.Ui.Cart.CartFragment
import com.example.khraw.Ui.Home.FoodList.FDBHelper
import com.example.khraw.Ui.Home.FragmentHome
import com.example.khraw.Ui.Home.FragmentProfile
import com.google.android.material.bottomnavigation.BottomNavigationView

class ActivityMenu() : AppCompatActivity(), Parcelable {

    constructor(parcel: Parcel) : this() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu)

        val bottomNavigation: BottomNavigationView = findViewById(R.id.nav_view)

        // ดึงข้อมูลจากฐานข้อมูล
        val dbHelper = FDBHelper(this)
        val itemList = dbHelper.getAllMenuItems().toMutableList()

        // แสดง FragmentHome เป็นหน้าแรก
        showFragment(FragmentHome.newInstance(ArrayList(itemList)))

        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    showFragment(FragmentHome.newInstance(ArrayList(itemList)))
                    true
                }

                R.id.navigation_order -> {
                    showFragment(CartFragment())
                    true
                }

                R.id.navigation_profile -> {
                    showFragment(FragmentProfile())
                    true
                }

                else -> false
            }
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.findFragmentById(R.id.frame_container) is FragmentHome) {
            super.onBackPressed()
            finishAffinity()
        } else {
            super.onBackPressed()
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ActivityMenu> {
        override fun createFromParcel(parcel: Parcel): ActivityMenu {
            return ActivityMenu(parcel)
        }

        override fun newArray(size: Int): Array<ActivityMenu?> {
            return arrayOfNulls(size)
        }
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_container, fragment)
            .addToBackStack(null)
            .commit()

    }
}

