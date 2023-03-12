package com.example.superhero_demo.model

import java.io.Serializable

data class SuperHero(
    val id: String,
    val name: String,
    val appearance: Appearance,
    val biography: Biography,
    val powerstats: Powerstats,
    val images: Image
): Serializable {
    data class Appearance(
        val gender: String,
        val race: String
    ): Serializable
    data class Powerstats(
        val intelligence: String,
        val strength: String,
        val speed: String,
        val durability: String,
        val power: String,
        val combat: String
    ): Serializable

    data class Biography(
        val fullName: String,
        val alignment: String
    ): Serializable

    data class Image(
        val sm: String
    ): Serializable

}
