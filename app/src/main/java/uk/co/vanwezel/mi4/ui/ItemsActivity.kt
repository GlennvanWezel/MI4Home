package uk.co.vanwezel.mi4.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.android.synthetic.main.activity_items.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import uk.co.vanwezel.mi4.R
import uk.co.vanwezel.mi4.data.model.Item
import uk.co.vanwezel.mi4.data.model.Room
import uk.co.vanwezel.mi4.data.model.Type
import uk.co.vanwezel.mi4.data.model.User

class ItemsActivity : AppCompatActivity() {


    private lateinit var mInterstitialAd: InterstitialAd

    val RC_SIGN_IN = 1000
    val firestoreInstance = FirebaseFirestore.getInstance()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MobileAds.initialize(this, getString(R.string.admob_app_id))
        setContentView(R.layout.activity_items)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(false)
        supportActionBar?.setDisplayUseLogoEnabled(true)
        supportActionBar?.setHomeButtonEnabled(false)


        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        bottom_nav.setupWithNavController(navController)
        NavigationUI.setupActionBarWithNavController(this, navController)

        signIn()
        //ADMOB TEST!!!
        //InterstitialAd does not need an element in the xm layout to display in
//        mInterstitialAd = InterstitialAd(this)
//        //done: replace with R.string.added_item_interstitial_ad once testing is done
//        mInterstitialAd.adUnitId = getString(R.string.testad_interstitial)
//
//        mInterstitialAd.loadAd(AdRequest.Builder().build())
//        mInterstitialAd.adListener = object: AdListener(){
//            override fun onAdLoaded() {
//                super.onAdLoaded()
//                mInterstitialAd.show()
//            }
//        }


    }

    private fun signIn() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build()
        )

        if (FirebaseAuth.getInstance().currentUser == null) {
            ActivityCompat.startActivityForResult(
                this,
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build(), RC_SIGN_IN, null
            )
        } else {
            FirebaseAuth.getInstance().updateCurrentUser(FirebaseAuth.getInstance().currentUser!!)
            Log.d("Login", "User was already logged in: ${FirebaseAuth.getInstance().currentUser?.email}")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.btn_log_out) {
            AuthUI.getInstance().signOut(this@ItemsActivity)
            signIn()
        }

        if(item?.itemId == R.id.home){

        }

        return super.onOptionsItemSelected(item)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == this.RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
                Log.d("USER", "USER: " + user.toString())
                if (response!!.isNewUser)
                    createFirestoreDBForNewUser(user)
                else
                    Log.d("USER LOGIN:", "Existing user: ${response.user.name}")

            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }

    private fun createFirestoreDBForNewUser(user: FirebaseUser?) {
        if (user != null) {
            GlobalScope.launch(Dispatchers.Main) {
                if (firestoreInstance.collection("users").document(user.uid).get().await() == null) {
                    val userEntity = User(
                        listOf(
                            Item("Laptop", "Bedroom", "Electronics", 1200.00)
                        ),
                        listOf(
                            Room(1, "Bedroom", 1200.00)
                        ),
                        listOf(
                            Type(1, "Electronics", 1200.00)
                        )
                    )
                    FirebaseFirestore
                        .getInstance()
                        .collection("users")
                        .document(user.uid)
                        .set(userEntity, SetOptions.merge())
                        .await()
                    Log.d("USER LOGIN:", "New user, database created")

                } else {
                }
            }
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }



    //region Helper methods
    fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.nav_host_fragment, fragment)
        fragmentTransaction.commit()
    }

    fun showToastLong(msg: String){
        Toast.makeText(baseContext,msg,Toast.LENGTH_LONG).show()
    }

    fun showToastshort(msg: String){
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show()
    }

    //endregion

}
