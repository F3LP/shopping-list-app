package com.facd.shoppinglist

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(val name: String, val quantity: Int, val collected: Boolean): Parcelable