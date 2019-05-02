package com.example.mi4.ui.items.manage

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.example.mi4.R
import com.example.mi4.data.db.entity.Item
import com.example.mi4.ui.ItemsViewModel
import com.example.mi4.utilities.InjectorUtils
import kotlinx.android.synthetic.main.add_item_fragment.*
import java.lang.StringBuilder

class AddItemFragment : Fragment() {

    companion object {
        fun newInstance() = AddItemFragment()
    }

    private lateinit var viewModel: AddItemViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_item_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AddItemViewModel::class.java)
        // TODO: Use the ViewModel
        initializeUi()

    }

    private fun initializeUi(){
        val factory = InjectorUtils.provideItemsViewModelFactory()
        val viewModel = ViewModelProviders.of(this, factory)
            .get(ItemsViewModel::class.java)
        viewModel.getItems().observe(this, Observer { items ->
            val stringBuilder = StringBuilder()
            items!!.forEach { item ->
                stringBuilder.append("$item\n\n")
            }
            textView_items.text = stringBuilder.toString()
        })

        button_add_item.setOnClickListener{
            val item = Item(
                editText_itemName.text.toString().trim()
                , editText_room.text.toString().trim()
                , editText_type.text.toString().trim()
                , if (editText_value.text.toString().trim() != "") {
                    editText_value.text.toString().toDouble()
                } else 0.00
            )


            viewModel.addItem(item)
            editText_itemName.setText("")
            editText_room.setText("")
            editText_type.setText("")
            editText_value.setText("")
        }
    }

}
