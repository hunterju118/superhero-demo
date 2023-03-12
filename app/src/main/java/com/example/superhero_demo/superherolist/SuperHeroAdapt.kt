package com.example.superhero_demo.superherolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.superhero_demo.databinding.SuperheroItemBinding
import com.example.superhero_demo.model.SuperHero
import com.squareup.picasso.Picasso

class SuperHeroAdapter(private val heroList: List<SuperHero>): RecyclerView.Adapter<SuperHeroAdapter.ViewHolder>() {
    private lateinit var binding: SuperheroItemBinding
    lateinit var mListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        mListener = listener
    }


    class ViewHolder(binding: SuperheroItemBinding, val listener: OnItemClickListener): RecyclerView.ViewHolder(binding.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = SuperheroItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding,mListener)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val superHero = heroList[position]

        binding.apply {
            heroId.text = superHero.id
            heroName.text = superHero.name
            Picasso.get().load(superHero.images.sm).into(heroImage);
            root.setOnClickListener {
                viewHolder.listener.onItemClick(position)
            }
        }
    }

    override fun getItemCount(): Int = heroList.size

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int) = position

    fun getItem(position: Int): SuperHero {
        return heroList[position]
    }
}