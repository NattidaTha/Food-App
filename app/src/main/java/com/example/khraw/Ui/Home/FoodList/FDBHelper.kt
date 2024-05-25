package com.example.khraw.Ui.Home.FoodList

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.khraw.R
import java.text.DecimalFormat

class FDBHelper(context: Context) : SQLiteOpenHelper(context, FDB_NAME, null, FDB_VERSION) {

    companion object {
        const val FDB_NAME = "foods.db"
        const val FDB_VERSION = 76

        const val TABLE_MENU = "Menu"
        const val COLUMN_MENU_ID = "MenuID"
        const val COLUMN_NAME = "NameFood"
        const val COLUMN_IMAGE_DRAWABLE = "ImageDrawable"
        const val COLUMN_PRICE = "PriceFood"
        const val COLUMN_QUANTITY = "Quantity"
        const val TABLE_SOUP_MENU = "SoupMenu"
        const val TABLE_FRIED_MENU = "FriedMenu"
        const val TABLE_SNACK_MENU = "SnackMenu"
        const val TABLE_DRINK_MENU = "DrinkMenu"
        const val TABLE_NEW_MENU = "NewMenu"
        const val TABLE_CART = "Cart"
        const val COLUMN_CART_ID = "CartID"
        const val COLUMN_CART_NAME = "Name"
        const val COLUMN_CART_PRICE = "Price"
        const val COLUMN_CART_IMAGE_DRAWABLE = "ImageDrawable"
        const val COLUMN_CART_QUANTITY = "ItemQuantity"
        const val TABLE_USER_CART = "UserCart"
        const val COLUMN_USER_CART_EMAIL = "UserEmail"
        const val COLUMN_USER_CART_ID = "CartID"
        const val COLUMN_USER_CART_NAME = "Name"
        const val COLUMN_USER_CART_PRICE = "Price"
        const val COLUMN_USER_CART_IMAGE_DRAWABLE = "ImageDrawable"
        const val COLUMN_USER_CART_QUANTITY = "ItemQuantity"

    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableMenuSQL = """
        CREATE TABLE $TABLE_MENU (
            $COLUMN_MENU_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COLUMN_IMAGE_DRAWABLE INTEGER,
            $COLUMN_NAME TEXT NOT NULL,
            $COLUMN_PRICE REAL NOT NULL,
            $COLUMN_QUANTITY INTEGER NOT NULL
        );
    """
        db.execSQL(createTableMenuSQL)

        val createTableSoupMenuSQL = """
        CREATE TABLE $TABLE_SOUP_MENU (
            $COLUMN_MENU_ID INTEGER PRIMARY KEY,
            FOREIGN KEY ($COLUMN_MENU_ID) REFERENCES $TABLE_MENU($COLUMN_MENU_ID)
        );
    """
        db.execSQL(createTableSoupMenuSQL)

        val createTableFriedMenuSQL = """
        CREATE TABLE $TABLE_FRIED_MENU (
            $COLUMN_MENU_ID INTEGER PRIMARY KEY,
            FOREIGN KEY ($COLUMN_MENU_ID) REFERENCES $TABLE_MENU($COLUMN_MENU_ID)
        );
    """
        db.execSQL(createTableFriedMenuSQL)

        val createTableSnackMenuSQL = """
        CREATE TABLE $TABLE_SNACK_MENU (
            $COLUMN_MENU_ID INTEGER PRIMARY KEY,
            FOREIGN KEY ($COLUMN_MENU_ID) REFERENCES $TABLE_MENU($COLUMN_MENU_ID)
        );
    """
        db.execSQL(createTableSnackMenuSQL)

        val createTableDrinkMenuSQL = """
        CREATE TABLE $TABLE_DRINK_MENU (
            $COLUMN_MENU_ID INTEGER PRIMARY KEY,
            FOREIGN KEY ($COLUMN_MENU_ID) REFERENCES $TABLE_MENU($COLUMN_MENU_ID)
        );
    """
        db.execSQL(createTableDrinkMenuSQL)

        val createTableNewMenuSQL = """
        CREATE TABLE $TABLE_NEW_MENU (
            $COLUMN_MENU_ID INTEGER PRIMARY KEY,
            FOREIGN KEY ($COLUMN_MENU_ID) REFERENCES $TABLE_MENU($COLUMN_MENU_ID)
        );
    """
        db.execSQL(createTableNewMenuSQL)

        val createTableCartSQL = """
        CREATE TABLE $TABLE_CART (
            $COLUMN_CART_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COLUMN_CART_NAME TEXT NOT NULL,
            $COLUMN_CART_PRICE REAL NOT NULL,
            $COLUMN_CART_IMAGE_DRAWABLE INTEGER NOT NULL,
            $COLUMN_CART_QUANTITY INTEGER NOT NULL
        );
    """
        db.execSQL(createTableCartSQL)

        val createTableUserCartSQL = """
    CREATE TABLE $TABLE_USER_CART (
        $COLUMN_USER_CART_EMAIL TEXT,
        $COLUMN_USER_CART_ID INTEGER,
        $COLUMN_USER_CART_NAME TEXT,
        $COLUMN_USER_CART_PRICE REAL,
        $COLUMN_USER_CART_IMAGE_DRAWABLE INTEGER,
        $COLUMN_USER_CART_QUANTITY INTEGER,
        PRIMARY KEY ($COLUMN_USER_CART_ID, $COLUMN_USER_CART_EMAIL),
        FOREIGN KEY ($COLUMN_USER_CART_ID) REFERENCES $TABLE_MENU($COLUMN_MENU_ID)
    );
"""
        db.execSQL(createTableUserCartSQL)

        // เพิ่มข้อมูลเข้าไปในตาราง
        addMenuItem("Tom Yum", 100, R.drawable.img_food1, 0, "soup", db)
        addMenuItem("Fried Rice", 120, R.drawable.img_food2, 0, "fried", db)
        addMenuItem("French Fries", 80, R.drawable.img_food3, 0, "snack", db)
        addMenuItem("Iced Tea", 50, R.drawable.img_food4, 0, "drink", db)
        addMenuItem("New",99,R.drawable.img_food5,0,"new",db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_MENU")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_SOUP_MENU")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_FRIED_MENU")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_SNACK_MENU")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_DRINK_MENU")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NEW_MENU")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CART")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USER_CART")
        onCreate(db)
    }

    fun addMenuItem(
        name: String,
        price: Int,
        imageDrawableName: Int,
        quantity: Int,
        category: String,
        db: SQLiteDatabase
    ) {
        val values = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_PRICE, price)
            put(COLUMN_IMAGE_DRAWABLE, imageDrawableName)
            put(COLUMN_QUANTITY, quantity)
        }

        val menuId = db.insert(TABLE_MENU, null, values)
        if (menuId != -1L) {
            val categoryTable = when (category) {
                "soup" -> TABLE_SOUP_MENU
                "fried" -> TABLE_FRIED_MENU
                "snack" -> TABLE_SNACK_MENU
                "drink" -> TABLE_DRINK_MENU
                "new"-> TABLE_NEW_MENU
                else -> throw IllegalArgumentException("Unknown category: $category")
            }

            val categoryValues = ContentValues().apply {
                put(COLUMN_MENU_ID, menuId)
            }

            db.insert(categoryTable, null, categoryValues)
        } else {
            throw IllegalStateException("Failed to add menu item: $name")
        }
    }

    fun searchMenuItems(query: String): List<FoodListAdapter.ItemFood> {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_MENU,
            null,
            "$COLUMN_NAME LIKE ?",
            arrayOf("%$query%"),
            null,
            null,
            null
        )

        val items = mutableListOf<FoodListAdapter.ItemFood>()
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MENU_ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
                val price = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE))
                val image = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_DRAWABLE))
                val quantity = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_QUANTITY))

                val decimalFormat = DecimalFormat("#,###")
                val formattedPrice = decimalFormat.format(price)

                items.add(FoodListAdapter.ItemFood(id, name, formattedPrice, image, quantity))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return items
    }

    fun getAllMenuItems(): List<FoodListAdapter.ItemFood> {
        val db = this.readableDatabase
        val cursor = db.query(TABLE_MENU, null, null, null, null, null, null)

        val items = mutableListOf<FoodListAdapter.ItemFood>()
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MENU_ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
                val price = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE))
                val image = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_DRAWABLE))
                val quantity = 0

                val decimalFormat = DecimalFormat("#,###")
                val formattedPrice = decimalFormat.format(price)

                items.add(FoodListAdapter.ItemFood(id, name, formattedPrice, image, quantity))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return items
    }


    fun getMenuItemsByCategory(category: String): List<FoodListAdapter.ItemFood> {
        val db = this.readableDatabase
        val tableName = when (category) {
            "soup" -> TABLE_SOUP_MENU
            "fried" -> TABLE_FRIED_MENU
            "snack" -> TABLE_SNACK_MENU
            "drink" -> TABLE_DRINK_MENU
            else -> throw IllegalArgumentException("Unknown category: $category")
        }

        val query =
            "SELECT * FROM $TABLE_MENU WHERE $COLUMN_MENU_ID IN (SELECT $COLUMN_MENU_ID FROM $tableName)"
        Log.d("FDBHelper", "Query: $query")  // เพิ่ม log เพื่อดูการคิวรี

        val cursor = db.rawQuery(query, null)
        val items = mutableListOf<FoodListAdapter.ItemFood>()
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MENU_ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
                val price = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE))
                val image = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_DRAWABLE))
                val quantity = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_QUANTITY))

                val decimalFormat = DecimalFormat("#,###")
                val formattedPrice = decimalFormat.format(price)

                items.add(FoodListAdapter.ItemFood(id, name, formattedPrice, image, quantity))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        Log.d("FDBHelper", "Items: $items")
        return items
    }

    fun newMenu(category: String): List<FoodListAdapter.ItemFood> {
        val db = this.readableDatabase
        val tableName = when (category) {
            "new menu" -> TABLE_NEW_MENU
            else -> throw IllegalArgumentException("Unknown category: $category")
        }

        val query =
            "SELECT * FROM $TABLE_MENU WHERE $COLUMN_MENU_ID IN (SELECT $COLUMN_MENU_ID FROM $tableName)"
        Log.d("FDBHelper", "Query: $query")  // เพิ่ม log เพื่อดูการคิวรี

        val cursor = db.rawQuery(query, null)
        val items = mutableListOf<FoodListAdapter.ItemFood>()
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MENU_ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
                val price = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE))
                val image = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_DRAWABLE))
                val quantity = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_QUANTITY))

                val decimalFormat = DecimalFormat("#,###")
                val formattedPrice = decimalFormat.format(price)

                items.add(FoodListAdapter.ItemFood(id, name, formattedPrice, image, quantity))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        Log.d("FDBHelper", "Items: $items")
        return items
    }

    fun addToCart(menuId: Int, menuName: String, menuPrice: Double, menuImageDrawable: Int, quantity: Int) {
        val db = this.writableDatabase

        // ลบรายการที่มี menuId เดียวกันในตะกร้า
        db.delete(TABLE_CART, "$COLUMN_CART_ID = ?", arrayOf(menuId.toString()))

        // คำนวณราคาใหม่โดยใช้ราคาของรายการนั้นๆ คูณด้วยจำนวนใหม่
        val newTotalPrice = menuPrice * quantity

        // เพิ่มรายการใหม่เข้าไปในตาราง CART โดยใช้ราคาใหม่และจำนวนใหม่
        val contentValues = ContentValues().apply {
            put(COLUMN_CART_ID, menuId)
            put(COLUMN_CART_NAME, menuName)
            put(COLUMN_CART_PRICE, newTotalPrice)
            put(COLUMN_CART_IMAGE_DRAWABLE, menuImageDrawable)
            put(COLUMN_CART_QUANTITY, quantity)
        }
        db.insert(TABLE_CART, null, contentValues)

        db.close()
    }
    fun getAllCartItems(): List<CartItem> {
        val db = this.readableDatabase
        val cursor = db.query(TABLE_CART, null, null, null, null, null, null)

        val items = mutableListOf<CartItem>()
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CART_ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CART_NAME))
                val price = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_CART_PRICE))
                val imageDrawable = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CART_IMAGE_DRAWABLE))
                val itemQuantity = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CART_QUANTITY))

                items.add(CartItem(id, name, price, imageDrawable,itemQuantity))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return items
    }

    fun removeFromCart(itemId: Int): Boolean {
            val db = writableDatabase
            return db.delete(TABLE_CART, "$COLUMN_CART_ID = ?", arrayOf(itemId.toString())) > 0
        }

    class CartItem(
        val id: Int,
        val name: String,
        val price: Double,
        val imageDrawable: Int,
        val itemQuantity: Int
    ) {
    }
}