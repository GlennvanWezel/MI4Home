package com.example.mi4.ui.items.list

import android.content.Context
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.mi4.R
import com.example.mi4.data.model.Item
import com.example.mi4.data.repository.ItemRepositoryImpl
import kotlinx.android.synthetic.main.recyclerview_item_row.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


/**
 * De inspiratie en kennis voor het gebruiken van een recycler view en het
 * aanmaken van de nodige adapters,etc komt van een youtube video:
 * bron: https://www.youtube.com/watch?v=jS0buQyfJfs
 * channel: Let's Build that app
 * video title: Kotlin Youtube: Intro to RecyclerView (Ep 1)
 *
 * Inspiratie en source voor the filteren van de recyclerview items komt van deze blogpost:
 * https://kotlincourses.com/searchview-filter-recyclerview-kotlin/
 */
class ItemRecyclerAdapter(private val context: Context, val itemlist : MutableList<Item>, val viewmodel: ItemListViewModel) : RecyclerView.Adapter<CustomViewHolder>(), Filterable {

    var itemSearchList: List<Item>? = null

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                   itemSearchList= itemlist
                } else {
                    val filteredList = ArrayList<Item>()
                    for (row in itemlist) {
                        if (row.naam.toLowerCase().contains(charString.toLowerCase()) || row.kamer.contains(charSequence) || row.type.contains(charSequence)) {
                            filteredList.add(row)
                        }
                    }
                   itemSearchList= filteredList
                }
                val filterResults = Filter.FilterResults()
                filterResults.values = itemSearchList
                return filterResults
            }
            override fun publishResults(charSequence: CharSequence, filterResults: Filter.FilterResults) {
               itemSearchList= filterResults.values as ArrayList<Item>
                notifyDataSetChanged()
            }
        }
    }

    init {
        itemSearchList = itemlist
    }
    override fun getItemCount(): Int {
        return itemlist.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.recyclerview_item_row,parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        if(!(position == itemSearchList!!.size) && !(position > itemSearchList!!.size)){
            val item = itemSearchList!![position]
            holder.view.itemName.text = item.naam
            holder.view.itemRoom.text = item.kamer
            holder.view.itemType.text = item.type
            holder.view.itemValue.text = item.waarde.toString()
            holder.view.ib_deleteitem.setOnClickListener {
                GlobalScope.launch{
                    viewmodel.deleteItem(item)
                }
                itemlist.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, itemlist.size)
            }
            holder.view.isVisible = true // re-show previously hidden items
        }
        else{
            holder.view.isVisible = false // hide items which were filtered-out
        }


    }

}

class CustomViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

}