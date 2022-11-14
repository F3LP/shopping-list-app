package com.facd.shoppinglist

import androidx.lifecycle.*
import com.facd.shoppinglist.model.Product
import kotlinx.coroutines.launch


class ProductViewModel(private val repository: ProductRepository) : ViewModel() {

    val getProducts: LiveData<List<Product>> = repository.getProducts.asLiveData()

    fun insert(product: Product) = viewModelScope.launch {
        repository.insert(product)
    }

    fun deleteAllProducts() {
        viewModelScope.launch {
            repository.deleteAll()
        }
    }

    fun deleteProduct(product: Product) {
        viewModelScope.launch {
            repository.deleteProduct(product)
        }
    }

}

class ProductViewModelFactory(private val repository: ProductRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}