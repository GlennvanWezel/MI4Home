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
        viewModel.items.observeForever {
            val ira = ItemRecyclerAdapter(it.toMutableList())
            rv_itemsList?.adapter = ira
        }
    }
}
