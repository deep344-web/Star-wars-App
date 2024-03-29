package com.example.starwarsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.starwarsapp.databinding.ActivityMainBinding
import com.example.starwarsapp.star_wars_characters.ui.StarWarsCharactersFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        if(savedInstanceState == null){
//            supportFragmentManager.commit{
//                setReorderingAllowed(true)
//                add<StarWarsCharactersFragment>(binding.id)
//            }
//        }
    }
}