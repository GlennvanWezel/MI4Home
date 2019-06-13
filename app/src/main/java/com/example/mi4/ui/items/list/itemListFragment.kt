package com.example.mi4.ui.items.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.doOnNextLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mi4.R
import kotlinx.android.synthetic.main.item_list_fragment.*

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
        rv_itemsList.visibility = View.INVISIBLE
        initialiseUi()

    }

    fun showLoading() {
        progressbar.visibility = View.VISIBLE
    }

    fun hideLoading() {
        progressbar.visibility = View.GONE
    }

    private fun initialiseUi() {
        showLoading()

        rv_itemsList.layoutManager = LinearLayoutManager(context)

        val ira = ItemRecyclerAdapter(context!!, mutableListOf(), viewModel)
        rv_itemsList?.adapter = ira

        rv_itemsList.doOnNextLayout {
            hideLoading()
            rv_itemsList.visibility = View.VISIBLE
        }


        viewModel.items.observeForever {
            ira.itemlist.clear()
            it.forEach {
                ira.itemlist.add(it)
            }
            ira.notifyDataSetChanged()
            rv_itemsList?.adapter = ira
        }

        sv_items!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // filter recycler view when query submitted
                ira.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                // filter recycler view when text is changed
                ira.filter.filter(query)
                return false
            }
        })
        btn_statistics.setOnClickListener {

        }

    }
}
