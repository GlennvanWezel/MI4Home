package com.example.mi4.ui.items.manage

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.mi4.R
import com.example.mi4.data.model.Item
import com.example.mi4.data.model.Room
import com.example.mi4.data.model.Type
import kotlinx.android.synthetic.main.add_item_fragment.*

class AddItemFragment : Fragment() {

    companion object {
        fun newInstance() = AddItemFragment()
    }

    private lateinit var viewModel: AddItemViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i("CREATION ORDER", "AddItemFragment | OnCreateView")

        return inflater.inflate(R.layout.add_item_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AddItemViewModel::class.java)
        // TODO: Use the ViewModel
        initializeUi()

    }

    private fun initializeUi(){

        val roomsArrayAdaptersObserver =
            ArrayAdapter<Room>(
                this.context!!,
                R.layout.support_simple_spinner_dropdown_item
            )
        viewModel.rooms.observeForever {
            roomsArrayAdaptersObserver.clear()
            it.forEach {
                roomsArrayAdaptersObserver.add(it)
            }
            //spinner_room.invalidate()
        }
        spinner_room.adapter = roomsArrayAdaptersObserver

        val typesArrayAdaptersObserver =
            ArrayAdapter<Type>(
                this.context!!,
                R.layout.support_simple_spinner_dropdown_item
            )

        viewModel.types.observeForever {
            typesArrayAdaptersObserver.clear()
            typesArrayAdaptersObserver.notifyDataSetChanged()
            typesArrayAdaptersObserver.addAll(it.toMutableList())
            typesArrayAdaptersObserver.notifyDataSetChanged()
        }
        this.spinner_type?.adapter = typesArrayAdaptersObserver



        button_add_item.setOnClickListener{
            clearErrorMessage()
            val validation = validateInput()
            if(validation["Result"] ?: error("VALIDATE INPUT: No Result on Validating Input")){
                val item = Item(
                    editText_itemName.text.toString().trim()
                    , (spinner_room.selectedItem as Room).name
                    , (spinner_type.selectedItem as Type).name
                    , if (editText_value.text.toString().trim() != "") {
                        editText_value.text.toString().toDouble()
                    } else 0.00
                )
                viewModel.addItem(item, (spinner_room.selectedItem as Room), (spinner_type.selectedItem as Type))
                editText_itemName.setText("")
                editText_value.setText("")
            }
            else{
                if(!validation.getValue("Name"))
                    editText_itemName.error = getString(R.string.error_name_cannot_be_empty)
                if(!validation.getValue("Value"))
                    editText_value.error = getString(R.string.error_value_cannot_be_empty)
                if(!validation.getValue("Room"))
                    textView_errors.text = getString(R.string.error_select_a_room)
                if(!validation.getValue("Type"))
                    textView_errors.text = getString(R.string.error_select_a_type)
            }
        }
    }

    private fun clearErrorMessage() {
        textView_errors.clearComposingText()
    }

    private fun validateInput(): Map<String,Boolean> {
        val result = mutableMapOf<String,Boolean>()
        // naam mag niet leeg zijn
        result["Name"] = !(editText_itemName.text.isEmpty() || editText_itemName.text == null)

        // value moet een double zijn en mag niet leeg zijn
        result["Value"] = !(editText_value.text.isEmpty() || editText_value.text == null)

        //room moet een geldige optie zijn
        result["Room"] = spinner_room.selectedItem != null

        //type moet een geldige optie zijn
        result["Type"] = spinner_type.selectedItem != null

        //Totaal resultaat optellen
        result["Result"] = result["Name"]==true&&result["Value"]==true&&result["Room"]==true&&result["Type"]==true

        return result
    }


}
