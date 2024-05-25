package com.example.khraw.Ui.Home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.khraw.R
import com.example.khraw.Ui.Home.FoodList.FDBHelper
import com.example.khraw.Ui.Home.FoodList.FoodListAdapter


class FragmentSearch : Fragment() ,FoodListAdapter.OnItemClickListener {

    private lateinit var adapterFoodList: FoodListAdapter
    private lateinit var dbHelper: FDBHelper
    private lateinit var allMenuItems: List<FoodListAdapter.ItemFood>
    private lateinit var recyclerViewFoodList: RecyclerView
    private lateinit var itemList: MutableList<FoodListAdapter.ItemFood>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_blank, container, false)

        itemList = mutableListOf()

        dbHelper = FDBHelper(requireContext())
        allMenuItems = dbHelper.getAllMenuItems()
        itemList.addAll(allMenuItems)

        recyclerViewFoodList = view.findViewById(R.id.recyclerView)
        recyclerViewFoodList.layoutManager = LinearLayoutManager(requireContext())
        adapterFoodList = FoodListAdapter(requireContext(), itemList, this)
        recyclerViewFoodList.adapter = adapterFoodList

        val searchView: SearchView = view.findViewById(R.id.searchView)

        view.post {
            val imm =
                requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(searchView, InputMethodManager.SHOW_IMPLICIT)
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    searchMenu(newText)
                }
                return true
            }
        })

        searchView.requestFocus()
        searchView.isIconified = false

        return view
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun searchMenu(query: String) {

        val searchedItems = dbHelper.searchMenuItems(query)

        if (searchedItems.isNotEmpty()) {
            itemList.clear()
            itemList.addAll(searchedItems)
            adapterFoodList.notifyDataSetChanged()
        } else {

            Toast.makeText(requireContext(), "No matching items found.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onButtonClick(position: Int) {
        if (position >= 0 && position < itemList.size) {
            val selectedItem = itemList[position]

            try {
                // ตรวจสอบให้แน่ใจว่า price ไม่เป็น null หรือว่างเปล่า
                if (!selectedItem.price.isNullOrEmpty()) {
                    val itemPrice = selectedItem.price.toDouble()
                    val totalPrice = selectedItem.quantity * itemPrice

                    dbHelper.addToCart(
                        selectedItem.id,
                        selectedItem.name,
                        itemPrice,
                        selectedItem.imageDrawable,
                        selectedItem.quantity
                    )

                    Toast.makeText(
                        requireContext(),
                        "Added ${selectedItem.quantity} of ${selectedItem.name} to cart",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Log.e("Error", e.message ?: "Unknown error occurred")
            }
        }
    }
}
