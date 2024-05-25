package com.example.khraw.Ui.Home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.khraw.R
import com.example.khraw.Ui.Home.FoodList.FDBHelper
import com.example.khraw.Ui.Home.FoodList.FoodListAdapter
import com.example.khraw.Ui.Home.FoodPromotion.ImageAdapter
import com.example.khraw.Ui.Home.FoodPromotion.MenuIconAdapter
import com.example.khraw.Ui.Oder.SharedViewModel


class FragmentHome : Fragment(), MenuIconAdapter.OnIconClickListener, ImageAdapter.OnIconClickListener, FoodListAdapter.OnItemClickListener {

    private lateinit var adapterFoodList: FoodListAdapter
    private lateinit var dbHelper: FDBHelper
    private lateinit var allMenuItems: List<FoodListAdapter.ItemFood>
    private lateinit var recyclerViewFoodList: RecyclerView
    override lateinit var itemList: MutableList<FoodListAdapter.ItemFood>
    private val sharedViewModel: SharedViewModel by activityViewModels()

    @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val sharedPref = requireActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val isSignedIn = sharedPref.getBoolean("isSignedIn", false)
        if (isSignedIn)

        itemList = arguments?.getParcelableArrayList("itemList") ?: mutableListOf()

        dbHelper = FDBHelper(requireContext())

        val slideModels = arrayListOf(
            SlideModel(
                R.drawable.papaya,
                "Delicious papaya salad, \n a popular food at the restaurant.",
                ScaleTypes.FIT
            ),
            SlideModel(
                R.drawable.food,
                "Fried chicken drumsticks, crispy on the outside and soft on the inside.",
                ScaleTypes.FIT
            ),
            SlideModel(R.drawable.food2, "Khanom Krok, delicious Thai dessert.", ScaleTypes.FIT)
        )
        val imageSlider: ImageSlider = view.findViewById(R.id.image_slider)
        imageSlider.setImageList(slideModels)


        val recyclerViewRecommended: RecyclerView = view.findViewById(R.id.recyclerViewImage)
        val adapterRecommended = ImageAdapter(requireContext(), this)
        recyclerViewRecommended.adapter = adapterRecommended
        recyclerViewRecommended.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)


        val recyclerViewIcons: RecyclerView = view.findViewById(R.id.recyclerViewFoodType)
        val iconAdapter = MenuIconAdapter(requireContext(), this)
        recyclerViewIcons.adapter = iconAdapter
        recyclerViewIcons.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)


        recyclerViewFoodList = view.findViewById(R.id.recyclerViewFoodList)
        recyclerViewFoodList.layoutManager = LinearLayoutManager(requireContext())


        if (itemList.isEmpty()) {

            allMenuItems = dbHelper.getAllMenuItems()
            itemList.addAll(allMenuItems)
        }

        adapterFoodList = FoodListAdapter(requireContext(), itemList, this)
        recyclerViewFoodList.adapter = adapterFoodList

        val search: Button = view.findViewById<Button?>(R.id.buttonSearch).apply {
            setOnClickListener {
                val fragment = FragmentSearch()

                val transaction = parentFragmentManager.beginTransaction()

                transaction.replace(R.id.frame_container, fragment)

                transaction.addToBackStack(null)

                transaction.commit()
            }
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        allMenuItems = dbHelper.getAllMenuItems()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onIconClick(category: String) {
        if (!::adapterFoodList.isInitialized) {
            Toast.makeText(
                requireContext(),
                "Data not ready yet. Please try again.",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val categoryLower = category.toLowerCase()
        when (categoryLower) {
            "menu" -> {
                itemList.clear()
                itemList.addAll(allMenuItems)
                adapterFoodList.notifyDataSetChanged()
                Toast.makeText(requireContext(), category, Toast.LENGTH_SHORT).show()
            }

            "new" -> {
                Toast.makeText(requireContext(), category, Toast.LENGTH_SHORT).show()
            }

            "recommend", "promotion" -> {
                Toast.makeText(requireContext(), category, Toast.LENGTH_SHORT).show()
            }

            "soup", "fried", "snack", "drink" -> {
                itemList.clear()
                val items = dbHelper.getMenuItemsByCategory(categoryLower)
                if (items.isNotEmpty()) {
                    itemList.addAll(items)
                    Toast.makeText(requireContext(), category, Toast.LENGTH_SHORT).show()
                }
                adapterFoodList.notifyDataSetChanged()
            }

            else -> {
            }
        }
    }

    override fun onButtonClick(position: Int) {
        if (position >= 0 && position < itemList.size) {
            val selectedItem = itemList[position]

            try {
                if (!selectedItem.price.isNullOrEmpty()) {
                    val itemPrice = selectedItem.price.toDouble()

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
    companion object {
        fun newInstance(itemList: ArrayList<FoodListAdapter.ItemFood>): FragmentHome {
            val fragment = FragmentHome()
            val args = Bundle()
            args.putParcelableArrayList("itemList", itemList)
            fragment.arguments = args
            return fragment
        }
    }
}
