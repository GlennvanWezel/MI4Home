package com.example.mi4.ui.items.list

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.mi4.R
import com.example.mi4.data.model.Item
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.item_list_fragment.*
import java.lang.Exception

class itemListFragment : Fragment() {

    companion object {
        fun newInstance() = itemListFragment()
    }

    private lateinit var viewModel: ItemListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.item_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(ItemListViewModel::class.java)
        // TODO: Use the ViewModel
        initialiseUi()

    }

    private fun initialiseUi() {
        rv_itemsList.layoutManager = LinearLayoutManager(this.context)
        val ira = ItemRecyclerAdapter(viewModel.items.value!!.toMutableList())
        rv_itemsList?.adapter = ira
        viewModel.items.observeForever {
            ira.itemlist.clear()
            it.forEach {
                ira.itemlist.add(it)
            }
            ira.notifyDataSetChanged()
            rv_itemsList.adapter = ira
        }
//        btn_statistics.setOnClickListener {
//            Log.d("RECYCLER VIEW","amount of items on display: ${ira.itemCount.toString()}, amount TO BE displayed: ${(viewModel.items.value as List<Item>).count().toString()}")
//        }
    }
}
