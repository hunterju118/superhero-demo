package com.example.superhero_demo.superherodetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.superhero_demo.databinding.FragmentSuperHeroDetailBinding
import com.example.superhero_demo.model.SuperHero
import com.squareup.picasso.Picasso

private const val SUPERHERO_KEY = ""

class SuperHeroDetailFragment : Fragment() {


    private lateinit var binding: FragmentSuperHeroDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSuperHeroDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupView()
    }

    private fun setupView() {
        val superHero = arguments?.getSerializable(SUPERHERO_KEY) as? SuperHero
        binding.apply outer@ {
            if (superHero != null) {
                with(superHero) {
                    ID.text = id
                    this@outer.name.text = name
                    fullName.text = biography.fullName
                    intelligence.text = powerstats.intelligence
                    strength.text = powerstats.strength
                    speed.text = powerstats.speed
                    durability.text = powerstats.durability
                    power.text = powerstats.power
                    combat.text = powerstats.combat
                    Picasso.get().load(images.sm).into(heroImage);
                    errorMassage.visibility = View.GONE
                }
            } else {
                errorMassage.visibility = View.VISIBLE
            }
        }
    }


    companion object {
        @JvmStatic
        fun newInstance(superHero: SuperHero) = SuperHeroDetailFragment().apply {
            arguments = Bundle().apply {
                putSerializable(SUPERHERO_KEY, superHero)
            }
        }
    }
}