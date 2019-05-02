package com.example.mi4.ui.items.list

import androidx.lifecycle.ViewModel;
import com.example.mi4.data.db.entity.Item

class ItemListViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    //lijst van objecten voor deze gebruiker
    lateinit var items : List<Item>

}
