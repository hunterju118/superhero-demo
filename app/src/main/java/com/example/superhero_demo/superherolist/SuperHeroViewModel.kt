package com.example.superhero_demo.superherolist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.superhero_demo.model.SuperHero
import com.example.superhero_demo.network.SuperHeroRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

sealed class SuperHeroData {
    data class SuperHeroSuccessData(val superHeroList: List<SuperHero>): SuperHeroData()
    data class SuperHeroFailureData(val errorMessage: String?): SuperHeroData()
}

class SuperHeroViewModelFactory(private val repository: SuperHeroRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SuperHeroViewModel::class.java)) {
            SuperHeroViewModel(repository) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}

class SuperHeroViewModel(
    private val repository: SuperHeroRepository
) : ViewModel() {

    private val _superHeroListLiveData = MutableLiveData<SuperHeroData>()
    val superHeroListLiveData: LiveData<SuperHeroData> = _superHeroListLiveData
    private var compositeDisposable = CompositeDisposable()

    fun loadSuperHeroList() {
        val disposable = repository.getSuperHeroList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { superHeroList ->
                    _superHeroListLiveData.postValue(
                        SuperHeroData.SuperHeroSuccessData(superHeroList)
                    )
                },
                {
                    _superHeroListLiveData.postValue(
                        SuperHeroData.SuperHeroFailureData(it.message)
                    )
                }
            )
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}