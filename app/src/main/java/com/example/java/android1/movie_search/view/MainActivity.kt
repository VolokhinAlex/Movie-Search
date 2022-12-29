package com.example.java.android1.movie_search.view

import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.java.android1.movie_search.R
import com.example.java.android1.movie_search.databinding.ActivityMainBinding
import com.example.java.android1.movie_search.utils.MyNetworkCallback
import com.example.java.android1.movie_search.utils.visible
import com.example.java.android1.movie_search.view.catalog.CatalogFragment
import com.example.java.android1.movie_search.view.contacts.ContactsFragment
import com.example.java.android1.movie_search.view.favorite.FavoriteFragment
import com.example.java.android1.movie_search.view.home.MainFragment
import com.example.java.android1.movie_search.view.search.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val mBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        val view = mBinding.root
        setContentView(view)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(mBinding.container.id, MainFragment.newInstance())
                .commitNow()
        }
        onItemSelectedInBottomNavigationBar()
        val networkCallback = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            networkCallback.registerDefaultNetworkCallback(MyNetworkCallback {
                Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()
            })
        }
    }

    private fun onItemSelectedInBottomNavigationBar() {
        val bottomNavigationView: BottomNavigationView = mBinding.bottomNavigation
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                mBinding.bottomNavigation.menu.findItem(R.id.action_home).itemId -> supportFragmentManager.popBackStack()
                mBinding.bottomNavigation.menu.findItem(R.id.action_search).itemId -> addFragment(
                    SearchFragment.newInstance()
                )
                mBinding.bottomNavigation.menu.findItem(R.id.action_catalog).itemId -> addFragment(
                    CatalogFragment.newInstance()
                )
                mBinding.bottomNavigation.menu.findItem(R.id.action_favorite).itemId -> addFragment(
                    FavoriteFragment.newInstance()
                )
            }
            return@setOnItemSelectedListener true
        }
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(mBinding.container.id, fragment).addToBackStack(null).setTransition(
                FragmentTransaction.TRANSIT_FRAGMENT_FADE
            )
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.menu_content_provider -> {
                supportFragmentManager.apply {
                    beginTransaction()
                        .replace(R.id.container, ContactsFragment.newInstance())
                        .addToBackStack("")
                        .commit()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val bottomNavigationView: BottomNavigationView = mBinding.bottomNavigation
        bottomNavigationView.selectedItemId =
            mBinding.bottomNavigation.menu.findItem(R.id.action_home).itemId
        bottomNavigationView.visible()
    }
}