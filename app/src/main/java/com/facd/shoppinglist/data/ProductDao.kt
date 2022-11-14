package com.facd.shoppinglist.data

import androidx.room.*
import com.facd.shoppinglist.model.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Query("SELECT * FROM product ORDER BY name ASC")
    fun getAll(): Flow<List<Product>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(product: Product)

    @Query("DELETE FROM product")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteProduct(product: Product)
}
