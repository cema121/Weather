package com.hungryshark.weather.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hungryshark.weather.R
import com.hungryshark.weather.databinding.MainActivityBinding
import com.hungryshark.weather.view.main.MainFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: MainActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }
}