package com.example.mi4.ui.items.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mi4.R
import com.example.mi4.data.model.Item
import com.example.mi4.data.repository.ItemRepositoryImpl
import kotlinx.android.synthetic.main.recyclerview_item_row.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


/**
 * De inspiratie en kennis voor het gebruiken van een recycler view en het
 * aanmaken van de nodige adapters,etc komt van een youtube video:
 * bron: https://www.youtube.com/watch?v=jS0buQyfJfs
 * channel: Let's Build that app
 * video title: Kotlin Youtube: Intro to RecyclerView (Ep 1)
 */
class ItemRecyclerAdapter(val itemlist : MutableList<Item>) : RecyclerView.Adapter<CustomViewHolder>() {

    override fun getItemCount(): Int {
        return itemlist.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.recyclerview_item_row,parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val item = itemlist[position]
        holder.view.itemName.text = item.naam
        holder.view.itemRoom.text = item.kamer
        holder.view.itemType.text = item.type
        holder.view.itemValue.text = item.waarde.toString()
        holder.view.ib_deleteitem.setOnClickListener {
            GlobalScope.launch {
                ItemRepositoryImpl().deleteItem(item)
            }
            itemlist.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, itemlist.size)
        }

    }

}

class CustomViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

}