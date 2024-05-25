package com.example.khraw.Ui.Home.FoodList

import android.annotation.SuppressLint
import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.khraw.R

class FoodListAdapter(
    private val context: Context,
    private var itemList: List<ItemFood>,
    val itemClickListener: OnItemClickListener,
) : RecyclerView.Adapter<FoodListAdapter.FoodViewHolder>() {

    inner class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemName: TextView = itemView.findViewById(R.id.text_foodName)
        val itemPrice: TextView = itemView.findViewById(R.id.text_price)
        val itemImage: ImageView = itemView.findViewById(R.id.image_food)
        val displayInteger: TextView = itemView.findViewById(R.id.integer_number)
        private val buttonAdd:Button =itemView.findViewById(R.id.increase)
        private val buttonRemove:Button = itemView.findViewById(R.id.decrease)
        private val buttonAddCart: ImageButton = itemView.findViewById(R.id.buttonAddCart)

        init {
            buttonAdd.setOnClickListener {
                increaseQuantity(adapterPosition)
            }

            buttonRemove.setOnClickListener {
                decreaseQuantity(adapterPosition)
            }

            buttonAddCart.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    itemClickListener.onButtonClick(position)
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onButtonClick(position: Int)
    }

    data class ItemFood(
        val id: Int,
        val name: String,
        val price: String,
        val imageDrawable: Int,
        var quantity: Int
    ) : Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString() ?: "",
            parcel.readString() ?: "",
            parcel.readInt(),
            parcel.readInt()
        )

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeInt(id)
            parcel.writeString(name)
            parcel.writeString(price)
            parcel.writeInt(imageDrawable)
            parcel.writeInt(quantity)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<ItemFood> {
            override fun createFromParcel(parcel: Parcel): ItemFood {
                return ItemFood(parcel)
            }

            override fun newArray(size: Int): Array<ItemFood?> {
                return arrayOfNulls(size)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_foodlist, parent, false)
        return FoodViewHolder(view)
    }

    @SuppressLint("StringFormatInvalid")
    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.itemName.text = currentItem.name
        val formattedPrice = currentItem.price + " THB"
        holder.itemPrice.text = formattedPrice
        holder.itemImage.setImageResource(currentItem.imageDrawable)
        holder.displayInteger.text = currentItem.quantity.toString()
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
    fun increaseQuantity(position: Int) {
        val currentItem = itemList[position]
        currentItem.quantity++
        notifyItemChanged(position)
    }

    fun decreaseQuantity(position: Int) {
        val currentItem = itemList[position]
        if (currentItem.quantity > 0) {
            currentItem.quantity--
            notifyItemChanged(position)
        }
    }
}
