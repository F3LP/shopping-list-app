package com.facd.shoppinglist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private var _items = MutableLiveData<Set<Product>>()
    val items: LiveData<Set<Product>> = _items

    fun setItems(newProducts: List<Product>){
        ProductsRepoFake.listProducts.addAll(newProducts)
    }

    fun getItems() {
        val products = ProductsRepoFake.listProducts
        viewModelScope.launch {
            _items.value = products
        }
    }

    fun clearList() {
        ProductsRepoFake.listProducts = mutableSetOf()
        val products = ProductsRepoFake.listProducts
        viewModelScope.launch {
            _items.value = products
        }
    }

    fun deleteItem(product: Product) {
        ProductsRepoFake.listProducts.remove(product)
    }

}