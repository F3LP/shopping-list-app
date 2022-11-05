package com.facd.shoppinglist

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.facd.shoppinglist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var products: ArrayList<Product>
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            val extras = intent.extras
            products = extras?.getParcelableArrayList<Product>("PRODUCTS") as ArrayList<Product>
            viewModel.setItems(products)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        viewModel.getItems()

        viewModel.items.observe(this) { items ->
            hideTextAndImageEmptyList(items.isNullOrEmpty())
            binding.recyclerView.also {
                it.layoutManager = LinearLayoutManager(this)
                it.setHasFixedSize(true)
                it.adapter = items?.let { listItems -> ListItemsAdapter(listItems) }

                binding.tvResultTotal.text = items.size.toString()
            }
        }

        binding.fab.setOnClickListener { addItem() }
        binding.btnClearList.setOnClickListener { viewModel.clearList() }
        binding.constraintBottom.visibility = View.GONE
    }

    private fun addItem() {
        val intent = Intent(this, AddItemActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun hideTextAndImageEmptyList(isEmpty: Boolean) {
        if (isEmpty) {
            binding.ivEmptyList.visibility = View.VISIBLE
            binding.tvEmptyList.visibility = View.VISIBLE
            binding.constraintBottom.visibility = View.GONE

        } else {
            binding.ivEmptyList.visibility = View.INVISIBLE
            binding.tvEmptyList.visibility = View.INVISIBLE
            binding.constraintBottom.visibility = View.VISIBLE
        }
    }

    private fun deleteItem(product: Product) {
        viewModel.deleteItem(product)
    }

}