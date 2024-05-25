package com.example.khraw.Ui.Oder

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.khraw.R
import com.example.khraw.Ui.Home.FoodList.FDBHelper


class CartListAdapter(
    private val context: Context,
    private var items: List<FDBHelper.CartItem>,
    private val loggedInEmail: String
) : RecyclerView.Adapter<CartListAdapter.ViewHolder>() {

    private var itemClickListener: OnItemClickListener? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemName: TextView = itemView.findViewById(R.id.itemName)
        val itemPrice: TextView = itemView.findViewById(R.id.itemPrice)
        val itemImage: ImageView = itemView.findViewById(R.id.itemImage)
        val itemNumber: TextView = itemView.findViewById(R.id.number)
        val removeItem: ImageView = itemView.findViewById(R.id.removeItem)

        init {
            removeItem.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION && itemClickListener != null) {
                    itemClickListener!!.onRemoveItemClick(position)
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onRemoveItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("StringFormatInvalid", "StringFormatMatches")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = items[position]
        holder.itemName.text = currentItem.name
        holder.itemPrice.text = context.getString(R.string.price_format, currentItem.price)
        holder.itemNumber.text = context.getString(R.string.item_quantity_format, currentItem.itemQuantity)
        holder.itemImage.setImageResource(currentItem.imageDrawable)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(newItems: List<FDBHelper.CartItem>) {
        items = newItems
        notifyDataSetChanged()
    }

    fun getTotalPrice(): Double {
        var totalPrice = 0.0
        for (item in items) {
            totalPrice += item.price
        }
        return totalPrice
    }
}
