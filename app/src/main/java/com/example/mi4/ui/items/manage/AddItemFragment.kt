package com.example.mi4.ui.items.manage

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.mi4.R
import com.example.mi4.data.model.Item
import com.example.mi4.data.model.Room
import com.example.mi4.data.model.Type
import kotlinx.android.synthetic.main.add_item_fragment.*
import kotlinx.android.synthetic.main.alert_dialog.view.*
import kotlinx.android.synthetic.main.alert_dialog_delete.*
import kotlinx.android.synthetic.main.alert_dialog_delete.view.*
import kotlin.collections.set

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

    @SuppressLint("InflateParams", "SetTextI18n")
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
            spinner_room?.invalidate()
        }
        spinner_room.adapter = roomsArrayAdaptersObserver

        val typesArrayAdaptersObserver =
            ArrayAdapter<Type>(
                this.context!!,
                R.layout.support_simple_spinner_dropdown_item
            )

        viewModel.types.observeForever {
            typesArrayAdaptersObserver.clear()
            it.forEach {
                typesArrayAdaptersObserver.add(it)
            }
            spinner_type?.invalidate()
        }
        this.spinner_type?.adapter = typesArrayAdaptersObserver

        btn_addRoom.setOnClickListener {
            val view = LayoutInflater.from(context).inflate(R.layout.alert_dialog, null)
            val builder = AlertDialog.Builder(this.context)
            builder.setView(view)
            builder.setCancelable(false)
                .setPositiveButton("OK") { _, _ ->
                    viewModel.addRoom(view.editTextDialogUserInput.text.toString())
                }
                .setNegativeButton("Cancel") { _, _ ->
                    //do nothing
                }
            val dialog = builder.create()
            dialog.show()
        }

        btn_addType.setOnClickListener {
            val view = LayoutInflater.from(context).inflate(R.layout.alert_dialog, null)
            val builder = AlertDialog.Builder(this.context)
            builder.setView(view)
            builder.setCancelable(false)
                .setPositiveButton("OK") { _, _ ->
                    viewModel.addType(view.editTextDialogUserInput.text.toString())
                }
                .setNegativeButton("Cancel") { _, _ ->
                    //do nothing
                }
            val dialog = builder.create()
            dialog.show()
        }
        btn_delete_room.setOnClickListener {
            val view = LayoutInflater.from(context).inflate(R.layout.alert_dialog_delete, null)
            view.spinner.adapter = roomsArrayAdaptersObserver
            view.spinner_new.adapter = roomsArrayAdaptersObserver
            view.chk_should_attached_items_be_moved.setOnCheckedChangeListener { _, isChecked ->
                view.spinner_new?.isEnabled = isChecked
            }
            val builder = AlertDialog.Builder(this.context)
            builder.setView(view)
            builder.setCancelable(false)
                .setPositiveButton("OK") { dialog, _ ->
                    if(view.spinner_new.selectedItemPosition == view.spinner.selectedItemPosition && view.chk_should_attached_items_be_moved.isChecked) {
                        Toast.makeText(this.context,"Cannot select the room/type for transfer that you are going to delete!", Toast.LENGTH_LONG).show()
                        dialog.cancel()
                    }
                    viewModel.deleteRoom(view.spinner.selectedItem as Room, view.chk_should_attached_items_be_moved.isChecked, view.spinner_new.selectedItem as Room?)
                }
                .setNegativeButton("Cancel") { _, _ ->

                }
            val dialog = builder.create()
            dialog.show()
        }

        btn_delete_type.setOnClickListener {
            val view = LayoutInflater.from(context).inflate(R.layout.alert_dialog_delete, null)
            view.spinner.adapter = typesArrayAdaptersObserver
            view.spinner_new.adapter = typesArrayAdaptersObserver
            view.chk_should_attached_items_be_moved.setOnCheckedChangeListener { _, isChecked ->
                view.spinner_new?.isEnabled = isChecked
            }
            val builder = AlertDialog.Builder(this.context)
            builder.setView(view)
            builder.setCancelable(false)
                .setPositiveButton("OK") { _, _ ->
                    var typeToMoveTo : Type? = null
                    if(view.chk_should_attached_items_be_moved.isChecked){
                        typeToMoveTo = spinner_new.selectedItem as Type?
                    }
                    viewModel.deleteType(view.spinner.selectedItem as Type,view.chk_should_attached_items_be_moved.isChecked, typeToMoveTo)
                }
                .setNegativeButton("Cancel") { _, _ ->
                    //do nothing
                }
            val dialog = builder.create()
            dialog.show()
        }



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
                textView_errors.text = ""
                textView_errors.visibility = View.VISIBLE

                if(!validation.getValue("Name")){
                    editText_itemName.error = getString(R.string.error_name_cannot_be_empty)
                    textView_errors.text = "${textView_errors.text} , ${getString(R.string.item_error_nameempty)}"
                }
                if(!validation.getValue("Value"))
                {
                    editText_value.error = getString(R.string.error_value_cannot_be_empty)
                    textView_errors.text = "${textView_errors.text} , ${getString(R.string.item_error_valueempty)}"
                }
                if(!validation.getValue("Room"))
                    textView_errors.text = getString(R.string.error_select_a_room)
                if(!validation.getValue("Type"))
                    textView_errors.text = getString(R.string.error_select_a_type)
            }
        }


    }

    private fun clearErrorMessage() {
        textView_errors.text = ""
        textView_errors.visibility = View.INVISIBLE
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
