package com.keelim.free.ui.main

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import coil.load
import com.google.android.material.snackbar.Snackbar
import com.keelim.core.extensions.showToast
import com.keelim.free.R
import com.keelim.free.databinding.ActivityMenuBinding
import com.keelim.free.databinding.AppBarMenuBinding
import com.keelim.free.databinding.NavHeaderMenuBinding
import com.keelim.free.ui.main.open.OpenActivity
import com.keelim.free.ui.main.search.SearchResultsActivity
import com.mocklets.pluto.Pluto
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private val binding: ActivityMenuBinding by lazy { ActivityMenuBinding.inflate(layoutInflater) }
    private val headerBinding: NavHeaderMenuBinding by lazy {
        NavHeaderMenuBinding.bind(binding.navView.getHeaderView(0))
    }
    private val barBinding: AppBarMenuBinding by lazy {
        AppBarMenuBinding.bind(binding.appBarMenu.root)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMenu.toolbar)
        initViews()
        Pluto.initialize(this)
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow), binding.drawerLayout)

        setupActionBarWithNavController(navController(), appBarConfiguration)
        binding.navView.setupWithNavController(navController())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        with(barBinding.searchBar) {
            maxWidth = Int.MAX_VALUE
            isSubmitButtonEnabled = true
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    val query = "Hello"
                    startActivity(Intent(this@MenuActivity,
                        SearchResultsActivity::class.java).apply {
                        action = Intent.ACTION_SEARCH
                        putExtra(SearchManager.QUERY, query)
                    })
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return true
                }
            })
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                navController().navigate(R.id.settingFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun navController() = findNavController(R.id.nav_host_fragment_content_menu)

    override fun onSupportNavigateUp(): Boolean {
        return navController().navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (!barBinding.searchBar.isIconified) {
            barBinding.searchBar.isIconified = true
        } else {
            super.onBackPressed()
        }
    }

    private fun initViews() = with(binding) {
        headerBinding.imageView.load("https://avatars.githubusercontent.com/u/26667456?v=4")

        appBarMenu.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            startActivity(Intent(this@MenuActivity, OpenActivity::class.java))
        }
    }
}