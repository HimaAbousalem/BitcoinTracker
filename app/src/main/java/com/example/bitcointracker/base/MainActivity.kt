package com.example.bitcointracker.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bitcointracker.R
import com.example.bitcointracker.databinding.ActivityMainBinding
import com.example.bitcointracker.features.main_screen.BitcoinHistoricalPriceFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, BitcoinHistoricalPriceFragment()).commit()
        }
    }
}