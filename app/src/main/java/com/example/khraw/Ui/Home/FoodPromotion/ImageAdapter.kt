package com.example.khraw.Ui.Home.FoodPromotion

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.khraw.R
import com.example.khraw.Ui.Home.FragmentHome

class ImageAdapter (private val context: Context, private val onIconClickListener: FragmentHome) :
    RecyclerView.Adapter<ImageAdapter.ImageHolder>() {

    private val icons = listOf(
        R.drawable.foodpromotion3,
        R.drawable.foodpromotion2,
        R.drawable.foodpromotion1,
        )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_image, parent, false)
        return ImageHolder(view)
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        val iconRes = icons[position]
        holder.iconImageView.setImageResource(iconRes)
        val categoryText = when (position) {
            0 -> "New"
            1 -> "Recommend"
            2 -> "Promotion"
            else -> "Unknown"
        }
        holder.iconTextView.text = categoryText
        holder.itemView.setOnClickListener {
            onIconClickListener.onIconClick(categoryText)
        }
    }

    interface OnIconClickListener {
        fun onIconClick(category: String)
    }

    override fun getItemCount(): Int {
        return icons.size
    }

    inner class ImageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iconImageView: ImageView = itemView.findViewById(R.id.iconFoods)
        val iconTextView: TextView = itemView.findViewById(R.id.textViewFoods)
    }
}
