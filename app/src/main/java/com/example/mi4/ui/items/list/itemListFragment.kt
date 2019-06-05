package com.example.mi4.ui.items.list

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.mi4.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.item_list_fragment.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import kotlin.math.log

class itemListFragment : Fragment() {

    companion object {
        fun newInstance() = itemListFragment()
    }

    private lateinit var viewModel: ItemListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i("CREATION ORDER", "itemListFragment | OnCreateView")
        return inflater.inflate(R.layout.item_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ItemListViewModel::class.java)
        // TODO: Use the ViewModel


    }

    override fun onStart() {
        if (ItemListTextView.text == null || ItemListTextView.text == getString(R.string.itemsnotyetloaded)){
            if (FirebaseAuth.getInstance().currentUser != null) {
                viewModel.getItems()
                viewModel.items.observeForever {
                    ItemListTextView.text = it.toString()
                }

            } else {
                ItemListTextView.text = getString(R.string.itemsnotyetloaded)
            }
        }
        Log.e("onstart:","itemlistfragment ONSTART, ${viewModel.items}")
        super.onStart()

    }

}
