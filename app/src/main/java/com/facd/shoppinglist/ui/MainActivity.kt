package com.facd.shoppinglist.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.facd.shoppinglist.ProductViewModel
import com.facd.shoppinglist.ProductViewModelFactory
import com.facd.shoppinglist.ProductsApplication
import com.facd.shoppinglist.databinding.ActivityMainBinding
import com.facd.shoppinglist.model.Product


class MainActivity : AppCompatActivity(), ListItemsAdapter.OnItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private var listOfProducts = ArrayList<Product>()

    private val viewModel: ProductViewModel by viewModels {
        ProductViewModelFactory((application as ProductsApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getProducts.observe(this) { items ->
            hideTextAndImageEmptyList(items.isNullOrEmpty())

            binding.recyclerView.also {
                it.layoutManager = LinearLayoutManager(this)
                it.setHasFixedSize(true)
                it.adapter = items?.let { listItems ->
                    listOfProducts = listItems as ArrayList<Product>
                    ListItemsAdapter(listItems, this)
                }

                binding.tvResultTotal.text = items.size.toString()
            }
        }

        binding.fab.setOnClickListener { addItem() }
        binding.btnClearList.setOnClickListener { viewModel.deleteAllProducts() }
        binding.barBottom.visibility = View.GONE
    }

    private fun addItem() {
        val intent = Intent(this, AddProductActivity::class.java)

        if (listOfProducts.isNotEmpty()) {
            intent.putParcelableArrayListExtra(PRODUCTS, listOfProducts)
        }
        startActivity(intent)
        finish()
    }

    private fun hideTextAndImageEmptyList(isEmpty: Boolean) {
        if (isEmpty) {
            binding.ivEmptyList.visibility = View.VISIBLE
            binding.tvEmptyList.visibility = View.VISIBLE
            binding.barBottom.visibility = View.GONE

        } else {
            binding.ivEmptyList.visibility = View.INVISIBLE
            binding.tvEmptyList.visibility = View.INVISIBLE
            binding.barBottom.visibility = View.VISIBLE
        }
    }

    override fun onItemClick(product: Product) {
        viewModel.deleteProduct(product)
    }

}