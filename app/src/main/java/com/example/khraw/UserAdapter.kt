package com.example.khraw

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(private var userList: List<Pair<String, String>>, private val userEmail: String) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val usernameTextView = itemView.findViewById<TextView>(R.id.usernameTextView)
        val emailTextView = itemView.findViewById<TextView>(R.id.emailTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_item, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val (username, email) = userList[position]
        if (email == userEmail) { // ตรวจสอบว่าอีเมลของผู้ใช้ตรงกับที่เข้าสู่ระบบหรือไม่
            holder.usernameTextView.text = username
            holder.emailTextView.text = email
        } else {
            holder.itemView.visibility = View.GONE // ซ่อนรายการที่ไม่ตรงกับอีเมลที่เข้าสู่ระบบ
            holder.itemView.layoutParams = RecyclerView.LayoutParams(0, 0) // ปรับขนาดรายการเป็น 0 เพื่อให้ไม่มีการแสดงผล
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}
