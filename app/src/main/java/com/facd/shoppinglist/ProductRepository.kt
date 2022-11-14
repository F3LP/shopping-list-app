package com.facd.shoppinglist

import com.facd.shoppinglist.data.ProductDao
import com.facd.shoppinglist.model.Product
import kotlinx.coroutines.flow.Flow

class ProductRepository(private val productDao: ProductDao) {

    val getProducts: Flow<List<Product>> = productDao.getAll()

    suspend fun deleteAll() {
        productDao.deleteAll()
    }

    suspend fun deleteProduct(product: Product) {
        productDao.deleteProduct(product)
    }

    suspend fun insert(product: Product) {
        productDao.insert(product)
    }
}