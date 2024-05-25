package com.example.khraw.Ui.Home.FoodPromotion

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.khraw.R
import com.example.khraw.Ui.Home.FoodList.FoodListAdapter
import com.example.khraw.Ui.Home.FragmentHome

class MenuIconAdapter(private val context: Context, private val onIconClickListener: FragmentHome) :
    RecyclerView.Adapter<MenuIconAdapter.IconViewHolder>() {

    private val icons = listOf(
        R.drawable.hatrobe,
        R.drawable.noodles,
        R.drawable.chicken1,
        R.drawable.jelly,
        R.drawable.cupjuice,

    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_foodtype, parent, false)
        return IconViewHolder(view)
    }

    override fun onBindViewHolder(holder: IconViewHolder, position: Int) {
        val iconRes = icons[position]
        holder.iconImageView.setImageResource(iconRes)
        val categoryText = when (position) {
            0 -> "Menu"
            1 -> "Soup"
            2 -> "Fried"
            3 -> "Snack"
            4 -> "Drink"
            else -> "Unknown"
        }
        holder.iconTextView.text = categoryText
        holder.itemView.setOnClickListener {
            onIconClickListener.onIconClick(categoryText)
        }
    }

    interface OnIconClickListener {
        val itemList: MutableList<FoodListAdapter.ItemFood>
        fun onIconClick(category: String)
    }

    override fun getItemCount(): Int {
        return icons.size
    }

    inner class IconViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iconImageView: ImageView = itemView.findViewById(R.id.iconFoodTypes)
        val iconTextView: TextView = itemView.findViewById(R.id.textViewTypes)
    }
}

