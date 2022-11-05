package com.facd.shoppinglist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.facd.shoppinglist.databinding.ActivityAddItemBinding

const val PRODUCTS = "PRODUCTS"
const val TAG_ERROR = "ERROR"

class AddItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddItemBinding
    private val products = ArrayList<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener { addProduct() }
    }

    override fun onSupportNavigateUp(): Boolean {
        val intent = Intent(this, MainActivity::class.java)
        if(products.isNotEmpty()) {
            intent.putParcelableArrayListExtra(PRODUCTS, products)
        }
        startActivity(intent)
        finish()
        return true
    }

    private fun addProduct() {
        val name = binding.tietProduct.text.toString()
        val quantity = binding.tietQuantity.text.toString()
        val collected = false

        if (name.isNotEmpty() && quantity.isNotEmpty()) {
            val product = Product(name, quantity.toInt(), collected)
            if(ProductsRepoFake.listProducts.contains(product)) {
                clearFields()
                Toast.makeText(this, R.string.already_registered, Toast.LENGTH_SHORT).show()
            } else {
                products.add(product)
                clearFields()
                Toast.makeText(this, R.string.registered, Toast.LENGTH_SHORT).show()
            }

            try {
                val imm: InputMethodManager =
                    getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
            } catch (exception: Exception) {
                Log.e(TAG_ERROR, "${exception.message}")
            }

            binding.tietQuantity.clearFocus()

        } else {
            Toast.makeText(this, R.string.fill_fields, Toast.LENGTH_SHORT).show()
        }

    }

    private fun clearFields() {
        binding.tietProduct.setText("")
        binding.tietQuantity.setText("")
    }

}