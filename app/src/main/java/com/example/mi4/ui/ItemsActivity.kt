package com.example.mi4.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.mi4.R
import com.example.mi4.data.db.entity.Item
import com.example.mi4.data.db.entity.Room
import com.example.mi4.data.db.entity.User
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseError
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_items.*

class ItemsActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    val RC_SIGN_IN = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_items)
        setSupportActionBar(toolbar)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        bottom_nav.setupWithNavController(navController)
        NavigationUI.setupActionBarWithNavController(this, navController)

        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build()
        )

// Create and launch sign-in intent
        if(FirebaseAuth.getInstance().currentUser == null){
            ActivityCompat.startActivityForResult(this,
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build(), RC_SIGN_IN, null
            )
        }
        val db = FirebaseFirestore.getInstance()
        var user = User(listOf(Item("Laptop","Bedroom","Electronics",1200.00))
                        , listOf(Room(1,"Bedroom",1200.00))
                        , listOf(com.example.mi4.data.db.entity.Type(1,"Electronics",1200.00)))
        db.collection("users").document().set(user)

//        var dbref = FirebaseDatabase.getInstance().reference
//
//        dbref= dbref.child("root").child("users")
//        dbref.addListenerForSingleValueEvent(object: ValueEventListener{
//            override fun onDataChange(p0: DataSnapshot) {
//                var json = p0.child("testuser").value.toString()
//                testview.text = json
//                var gson = Gson()
//                var usercompiled = gson.fromJson(json, User::class.java)
//                testview.text = usercompiled.toString()


//                for (item in items) {
//
//                }
//
//                testview.text = user.toString()
//            }
//
//            override fun onCancelled(p0: DatabaseError) {
//
//
//            }
//        })


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser

            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController,null)
    }

    public fun replaceFragment(fragment: Fragment){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.nav_host_fragment, fragment)
        fragmentTransaction.commit()
    }
}
