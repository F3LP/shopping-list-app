package com.facd.shoppinglist.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.facd.shoppinglist.*
import com.facd.shoppinglist.databinding.ActivityAddItemBinding
import com.facd.shoppinglist.model.Product

const val PRODUCTS = "PRODUCTS"
const val TAG_ERROR = "ERROR"

class AddProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddItemBinding
    private var products = ArrayList<Product>()

    private val viewModel: ProductViewModel by viewModels {
        ProductViewModelFactory((application as ProductsApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener { addProduct() }

        try {
            val extras = intent.extras
            products = extras?.getParcelableArrayList<Product>(PRODUCTS) as ArrayList<Product>
            products.forEach { it.id = null }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()

        return true
    }

    private fun addProduct() {
        val name = binding.tietProduct.text.toString()
        val quantity = binding.tietQuantity.text.toString()

        if (name.isNotEmpty() && quantity.isNotEmpty()) {
            val product =
                Product(id = null, name = name.trim(), quantity = quantity.toInt())
            if (!products.contains(product)) {
                Toast.makeText(this, R.string.registered, Toast.LENGTH_SHORT).show()
                viewModel.insert(product)
                products.add(product)
                clearFields()
            } else {
                clearFields()
                Toast.makeText(this, R.string.already_registered, Toast.LENGTH_SHORT).show()
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