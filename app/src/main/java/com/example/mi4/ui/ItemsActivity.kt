package com.example.mi4.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.mi4.R
import com.example.mi4.data.model.Item
import com.example.mi4.data.model.Room
import com.example.mi4.data.model.Type
import com.example.mi4.data.model.User
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.android.synthetic.main.activity_items.*

class ItemsActivity : AppCompatActivity() {

    val RC_SIGN_IN = 1000

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_items)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        bottom_nav.setupWithNavController(navController)
        NavigationUI.setupActionBarWithNavController(this, navController)

        signIn()

    }

    private fun signIn() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build()
        )

        if(FirebaseAuth.getInstance().currentUser == null){
            ActivityCompat.startActivityForResult(this,
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build(), RC_SIGN_IN, null
            )
        }
        else{
            FirebaseAuth.getInstance().updateCurrentUser(FirebaseAuth.getInstance().currentUser!!)
            Log.d("Login","User was already logged in: ${FirebaseAuth.getInstance().currentUser?.uid}")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.btn_log_out){
            AuthUI.getInstance().signOut(this@ItemsActivity)
            signIn()
        }

        return super.onOptionsItemSelected(item)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == this.RC_SIGN_IN) {
            //val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
                Log.d("USER", "USER: " + user.toString())
                createFirestoreDBForNewUser(user)
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }

    private fun createFirestoreDBForNewUser(user: FirebaseUser?) {
        if(user != null){
            val userEntity = User(
                listOf(
                    Item("Laptop","Bedroom","Electronics",1200.00)
                ),
                listOf(
                    Room(1,"Bedroom",1200.00)
                ),
                listOf(
                    Type(1,"Electronics",1200.00)
                ))
            FirebaseFirestore
                .getInstance()
                .collection("users")
                .document(user.uid)
                .set(userEntity, SetOptions.merge())

        }
    }



    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController,null)
    }

    fun replaceFragment(fragment: Fragment){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.nav_host_fragment, fragment)
        fragmentTransaction.commit()
    }


}
