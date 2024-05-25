package com.example.khraw.Ui.Cart

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.khraw.R
import com.example.khraw.Ui.Home.FoodList.FDBHelper
import com.example.khraw.Ui.Oder.CartListAdapter

class CartFragment : Fragment(), CartListAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CartListAdapter
    private lateinit var dbHelper: FDBHelper
    private lateinit var totalPriceTextView: TextView
    private lateinit var fragmentBitmap: Bitmap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cart, container, false)

        val sharedPref = requireActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val isSignedIn = sharedPref.getBoolean("isSignedIn", false)
        val loggedInEmail = sharedPref.getString("loggedInEmail", "") ?: ""

        dbHelper = FDBHelper(requireContext())
        adapter = CartListAdapter(requireContext(), listOf(), "")


        recyclerView = view.findViewById(R.id.recycler_view_cart)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        totalPriceTextView = view.findViewById(R.id.totalPriceTextView)

        adapter.setOnItemClickListener(this)
        recyclerView.adapter = adapter

        updateTotalPrice()

        val saveButton: ImageButton = view.findViewById(R.id.saveImageButton)
        saveButton.setOnClickListener {
            fragmentBitmap = getBitmapFromView(view)
            saveBitmapToDevice(fragmentBitmap)
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        adapter.updateItems(dbHelper.getAllCartItems())
        updateTotalPrice()
    }

    override fun onDestroy() {
        super.onDestroy()
        dbHelper.close()
    }

    override fun onRemoveItemClick(position: Int) {
        val cartItems = dbHelper.getAllCartItems()
        val itemToRemove = cartItems[position]
        dbHelper.removeFromCart(itemToRemove.id)
        adapter.updateItems(dbHelper.getAllCartItems())
        updateTotalPrice()
    }

    private fun getBitmapFromView(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    @SuppressLint("InlinedApi")
    private fun saveBitmapToDevice(bitmap: Bitmap) {
        val filename = "Food_Bill_${System.currentTimeMillis()}.png"

        val resolver = requireActivity().contentResolver
        val imageCollection = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)

        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, filename)
            put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            put(MediaStore.Images.Media.WIDTH, bitmap.width)
            put(MediaStore.Images.Media.HEIGHT, bitmap.height)
        }

        val imageUri = resolver.insert(imageCollection, contentValues)

        if (imageUri != null) {
            val outputStream = resolver.openOutputStream(imageUri)
            if (outputStream != null) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            }
            outputStream?.close()
            Toast.makeText(requireContext(), "บันทึกรูปภาพเรียบร้อยแล้ว", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "ไม่สามารถบันทึกรูปภาพได้", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("StringFormatMatches")
    private fun updateTotalPrice() {
        val totalPrice = adapter.getTotalPrice()
        totalPriceTextView.text = getString(R.string.total_price_format, totalPrice)
    }
}
