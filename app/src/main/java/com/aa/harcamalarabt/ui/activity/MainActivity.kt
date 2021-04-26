package com.aa.harcamalarabt.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aa.harcamalarabt.R
import com.aa.harcamalarabt.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
    }

}