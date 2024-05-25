package com.example.khraw.Ui.Oder

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.khraw.Ui.Home.FoodPromotion.FoodItem

class SharedViewModel : ViewModel() {
    val totalPrice = MutableLiveData<Int>()
    val selectedItems = MutableLiveData<List<FoodItem>>()

    init {
        totalPrice.value = 0
        selectedItems.value = listOf()
    }

    fun updateTotalPrice(price: Int) {
        totalPrice.value = price
    }

    fun updateSelectedItems(items: List<FoodItem>) {
        selectedItems.value = items
    }
}
