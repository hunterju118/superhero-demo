package com.example.superhero_demo.network

import com.example.superhero_demo.model.SuperHero
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface SuperHeroService {
    @GET("all.json")
    fun getSuperHeroList(): Observable<List<SuperHero>>
}