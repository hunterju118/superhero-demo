package com.example.superhero_demo.network

import com.example.superhero_demo.model.SuperHero
import io.reactivex.rxjava3.core.Observable

interface SuperHeroRepository {
    fun getSuperHeroList(): Observable<List<SuperHero>>
}


class SuperHeroRepositoryImpl: SuperHeroRepository {
    private val superHeroService: SuperHeroService = RetrofitClientInstance.retrofitInstance.create(
        SuperHeroService::class.java
    )

    override fun getSuperHeroList(): Observable<List<SuperHero>> {
        return superHeroService.getSuperHeroList()
    }
}