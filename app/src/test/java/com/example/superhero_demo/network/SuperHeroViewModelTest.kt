package com.example.superhero_demo.network

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.superhero_demo.RxImmediateSchedulerRule
import com.example.superhero_demo.model.SuperHero
import com.example.superhero_demo.superherolist.SuperHeroData
import com.example.superhero_demo.superherolist.SuperHeroViewModel
import io.reactivex.rxjava3.core.Observable
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever


class SuperHeroViewModelTest {

    private lateinit var viewModel: SuperHeroViewModel
    private val repository: SuperHeroRepository = mock()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    companion object {
        @ClassRule
        @JvmField
        val rxSchedulerRule = RxImmediateSchedulerRule()
    }

    @Before
    fun setup() {
        viewModel = SuperHeroViewModel(repository)
    }

    @Test
    fun `load super hero list with correct answer`() {
        val superHeroList: List<SuperHero> = listOf(createSuperHero())
        val observable = Observable.just(superHeroList)
        whenever(repository.getSuperHeroList()).thenReturn(observable)

        viewModel.loadSuperHeroList()
        val result = viewModel.superHeroListLiveData.value

        assertTrue(result is SuperHeroData.SuperHeroSuccessData)
        assertEquals(superHeroList, (result as SuperHeroData.SuperHeroSuccessData).superHeroList)
    }

    @Test
    fun `load super hero list with error`() {
        val observable = Observable.error<List<SuperHero>>(Exception("There is an error here"))
        whenever(repository.getSuperHeroList()).thenReturn(observable)

        viewModel.loadSuperHeroList()
        val result = viewModel.superHeroListLiveData.value

        assertTrue(result is SuperHeroData.SuperHeroFailureData)
        assertEquals("There is an error here", (result as SuperHeroData.SuperHeroFailureData).errorMessage)
    }

    private fun createSuperHero(): SuperHero =
        SuperHero(
            "id",
            "name",
            SuperHero.Appearance("gender", "race"),
            SuperHero.Biography("full name", "alignment"),
            SuperHero.Powerstats(
                "intelligence",
                "strength",
                "speed",
                "durability",
                "power",
                "combat"
            ),
            SuperHero.Image("sm")
        )
}