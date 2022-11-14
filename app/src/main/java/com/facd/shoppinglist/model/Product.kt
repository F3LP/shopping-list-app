package com.facd.shoppinglist.model

import android.os.Parcelable
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Dao
@Entity(tableName = "product")
@Parcelize
data class Product(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    val name: String,
    val quantity: Int,
) : Parcelable
