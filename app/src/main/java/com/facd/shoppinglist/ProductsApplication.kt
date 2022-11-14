package com.facd.shoppinglist

import android.app.Application
import com.facd.shoppinglist.data.ProductRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class ProductsApplication : Application() {

    private val applicationScope = CoroutineScope(SupervisorJob())

    private val database by lazy { ProductRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { ProductRepository(database.productDao()) }
}