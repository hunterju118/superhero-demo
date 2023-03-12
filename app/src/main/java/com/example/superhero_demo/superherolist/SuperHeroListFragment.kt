package com.example.superhero_demo.superherolist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.superhero_demo.R
import com.example.superhero_demo.databinding.FragmentSuperHeroListBinding
import com.example.superhero_demo.model.SuperHero
import com.example.superhero_demo.superherodetail.SuperHeroDetailFragment
import androidx.fragment.app.viewModels
import com.example.superhero_demo.network.SuperHeroRepository
import com.example.superhero_demo.network.SuperHeroRepositoryImpl

class SuperHeroListFragment : Fragment() {
    private lateinit var binding: FragmentSuperHeroListBinding

    private val repository: SuperHeroRepository = SuperHeroRepositoryImpl()

    private val viewModel: SuperHeroViewModel by viewModels {
        SuperHeroViewModelFactory(repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSuperHeroListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.spinner.visibility = View.VISIBLE
        viewModel.loadSuperHeroList()

        viewModel.superHeroListLiveData.observe(viewLifecycleOwner) { superHeroData ->
            binding.spinner.visibility = View.GONE

            when (superHeroData) {
                is SuperHeroData.SuperHeroSuccessData -> updateSuperHeroList(superHeroData.superHeroList)
                is SuperHeroData.SuperHeroFailureData -> updateErrorMessage(superHeroData.errorMessage)
            }
        }
    }

    private fun updateSuperHeroList(superHeroList: List<SuperHero>) {
        val adapter = SuperHeroAdapter(superHeroList)
        binding.apply {
            errorMassage.visibility = View.GONE
            superheroList.visibility = View.VISIBLE

            superheroList.adapter = adapter
            adapter.setOnItemClickListener(object : SuperHeroAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    val selectedSuperhero = adapter.getItem(position)
                    navigateToSuperheroDetailFragment(selectedSuperhero)
                }
            })
        }
    }

    private fun navigateToSuperheroDetailFragment(superHero: SuperHero) {
        val detailFragment = SuperHeroDetailFragment.newInstance(superHero)
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.container, detailFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun updateErrorMessage(errorMessage: String?) {
        binding.apply {
            superheroList.visibility = View.GONE
            errorMassage.visibility = View.VISIBLE

            errorMessage?.let { errorMassage.text = it }
        }
    }
}