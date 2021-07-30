package com.hungryshark.weather.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hungryshark.weather.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(getLayoutInflater())
        val view = binding.getRoot()
        setContentView(view)
    }
}