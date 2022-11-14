package com.facd.shoppinglist.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.facd.shoppinglist.model.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Product::class], version = 1, exportSchema = false)
abstract class ProductRoomDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao

    private class ProductDatabaseCallback(
        private val scope: CoroutineScope
    ) : Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val productDao = database.productDao()

                    productDao.deleteAll()
                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ProductRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): ProductRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = databaseBuilder(
                    context.applicationContext,
                    ProductRoomDatabase::class.java,
                    "product_database"
                )
                    .addCallback(ProductDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
