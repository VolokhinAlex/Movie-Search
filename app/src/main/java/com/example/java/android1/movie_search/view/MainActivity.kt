package com.example.java.android1.movie_search.view

import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.java.android1.movie_search.R
import com.example.java.android1.movie_search.databinding.ActivityMainBinding
import com.example.java.android1.movie_search.utils.MyNetworkCallback
import com.example.java.android1.movie_search.utils.visible
import com.example.java.android1.movie_search.view.catalog.CatalogFragment
import com.example.java.android1.movie_search.view.home.MainFragment
import com.example.java.android1.movie_search.view.profile.ProfileFragment
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
                mBinding.bottomNavigation.menu.findItem(R.id.action_profile).itemId -> addFragment(
                    ProfileFragment.newInstance()
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

    override fun onBackPressed() {
        super.onBackPressed()
        val bottomNavigationView: BottomNavigationView = mBinding.bottomNavigation
        bottomNavigationView.selectedItemId =
            mBinding.bottomNavigation.menu.findItem(R.id.action_home).itemId
        bottomNavigationView.visible()
    }
}