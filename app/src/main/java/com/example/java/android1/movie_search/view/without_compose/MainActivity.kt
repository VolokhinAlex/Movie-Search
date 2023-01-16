package com.example.java.android1.movie_search.view.without_compose

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
import com.example.java.android1.movie_search.view.without_compose.favorite.FavoriteFragment
import com.example.java.android1.movie_search.view.without_compose.home.MainFragment
import com.example.java.android1.movie_search.view.without_compose.search.SearchFragment
import com.example.java.android1.movie_search.view.without_compose.theaters.MapFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, MainFragment.newInstance())
                .commit()
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
        val bottomNavigationView: BottomNavigationView = binding.bottomNavigation
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                binding.bottomNavigation.menu.findItem(R.id.action_home).itemId -> supportFragmentManager.popBackStack()
                binding.bottomNavigation.menu.findItem(R.id.action_search).itemId -> addFragment(
                    SearchFragment.newInstance()
                )
                binding.bottomNavigation.menu.findItem(R.id.action_favorite).itemId -> addFragment(
                    FavoriteFragment.newInstance()
                )
            }
            return@setOnItemSelectedListener true
        }
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(binding.container.id, fragment).addToBackStack(null).setTransition(
                FragmentTransaction.TRANSIT_FRAGMENT_FADE
            )
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_map -> {
                supportFragmentManager.apply {
                    beginTransaction()
                        .replace(R.id.container, MapFragment.newInstance())
                        .addToBackStack(null)
                        .commit()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val bottomNavigationView: BottomNavigationView = binding.bottomNavigation
        bottomNavigationView.selectedItemId =
            binding.bottomNavigation.menu.findItem(R.id.action_home).itemId
        bottomNavigationView.visible()
    }
}