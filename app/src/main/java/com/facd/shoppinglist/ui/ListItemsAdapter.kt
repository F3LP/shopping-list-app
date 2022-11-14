package com.facd.shoppinglist.ui

import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.facd.shoppinglist.R
import com.facd.shoppinglist.model.Product

class ListItemsAdapter(
    private var listProducts: List<Product>,
    private val clickListener: OnItemClickListener,
) : RecyclerView.Adapter<ListItemsAdapter.ViewHolder>() {

    inner class ViewHolder(view: View, listener: OnItemClickListener) :
        RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.tv_name)
        val quantity: TextView = view.findViewById(R.id.tv_quantity)
        private val btnRemove: ImageButton = view.findViewById(R.id.ic_delete)

        init {
            btnRemove.setOnClickListener {
                val item = listProducts.elementAt(adapterPosition)
                listener.onItemClick(item)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(product: Product)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_list, viewGroup, false)

        return ViewHolder(view, clickListener)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        with(viewHolder) {
            name.text = listProducts.elementAt(position).name
            quantity.text = listProducts.elementAt(position).quantity.toString()
        }
    }

    override fun getItemCount() = listProducts.size

}