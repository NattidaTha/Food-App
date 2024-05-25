package com.example.khraw.Ui.Home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.khraw.ActivityLogin
import com.example.khraw.ActivityMain
import com.example.khraw.ActivityRegister
import com.example.khraw.DBHelper
import com.example.khraw.R
import com.example.khraw.UserAdapter

class FragmentProfile : Fragment() {

    private lateinit var dbHelper: DBHelper
    private lateinit var userAdapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val sharedPref = requireActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val isSignedIn = sharedPref.getBoolean("isSignedIn", false)
        val loggedInEmail = sharedPref.getString("loggedInEmail", "") ?: ""


        if (isSignedIn) {
            dbHelper = DBHelper(requireContext())
            val userList = dbHelper.getAllUsers()
            val userEmail = loggedInEmail

            val userAdapter = UserAdapter(userList, userEmail)

            val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = userAdapter

            val buttonLogout: Button = view.findViewById(R.id.button_logout)
            buttonLogout.visibility = View.VISIBLE
            buttonLogout.setOnClickListener {

                val sharedPrefEditor = sharedPref.edit()
                sharedPrefEditor.putBoolean("isSignedIn", false)
                sharedPrefEditor.apply()

                val intent = Intent(requireContext(), ActivityLogin::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        } else {
            val intent = Intent(requireContext(), ActivityLogin::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
        return view
    }
}
