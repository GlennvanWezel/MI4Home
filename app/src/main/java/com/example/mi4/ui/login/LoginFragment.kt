package com.example.mi4.ui.login

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.example.mi4.R
import com.example.mi4.ui.ItemsActivity
import com.example.mi4.ui.items.list.itemListFragment
import kotlinx.android.synthetic.main.login_fragment.*

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        login_btn.setOnClickListener {
           var succes = viewModel.login(editText_username.text.toString().trim(), editText_password.text.toString().trim())
            if(succes != null && succes.toString().equals("success"))
                (activity as ItemsActivity).replaceFragment(itemListFragment())
            if(succes != null)
                Toast.makeText(context, "Login failed!"+ succes.toString(), Toast.LENGTH_SHORT).show()
            Toast.makeText(context, "Login failed! success is null!", Toast.LENGTH_SHORT).show()


        }
    }

}
