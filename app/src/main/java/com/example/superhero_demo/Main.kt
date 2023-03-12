package com.example.superhero_demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.superhero_demo.databinding.ActivityMainBinding
import com.example.superhero_demo.superherolist.SuperHeroListFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val superHeroListFragment = SuperHeroListFragment()
        supportFragmentManager.beginTransaction().replace(binding.container.id, superHeroListFragment).commit()

    }


}