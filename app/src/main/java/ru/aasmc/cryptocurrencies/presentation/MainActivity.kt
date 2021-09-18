package ru.aasmc.cryptocurrencies.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.add
import androidx.fragment.app.commit
import ru.aasmc.cryptocurrencies.R
import ru.aasmc.cryptocurrencies.presentation.coinlist.CoinListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        // Switch to AppTheme for displaying the activity
        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<CoinListFragment>(R.id.fragment_container)
            }
        }
    }

    override fun onBackPressed() {
        hideUpButton()
        super.onBackPressed()
    }

    override fun onSupportNavigateUp(): Boolean {
        navigateUp()
        hideUpButton()
        return super.onSupportNavigateUp()
    }

    private fun navigateUp() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStackImmediate()
        }
    }

    private fun hideUpButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }
}