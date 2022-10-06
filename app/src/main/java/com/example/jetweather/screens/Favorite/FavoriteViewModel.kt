package com.example.jetweather.screens.Favorite

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetweather.model.Favorite
import com.example.jetweather.repository.WeatherDbRepository

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repository: WeatherDbRepository) : ViewModel(){

    private val _favList = MutableStateFlow<List<Favorite>>(emptyList())

    val favList = _favList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getFavorites().distinctUntilChanged()
                .collect{ listOfFav ->
                    if (listOfFav.isNullOrEmpty()){
                        Log.d("TAG",": Empty Favs")
                    }else {
                        _favList.value = listOfFav
                        Log.d("TAG",": ${favList.value }")
                    }
                }
        }
    }

    fun insertFavorite(favorite: Favorite) = viewModelScope.launch {
        repository.insertFavorite(favorite)
    }

    fun deleteAllFavorite() = viewModelScope.launch {
        repository.deleteAllFavorites()
    }

    fun deleteFavorite(favorite: Favorite) = viewModelScope.launch {
        repository.deleteFavorite(favorite)
    }

    fun updateFavorite(favorite: Favorite) = viewModelScope.launch {
        repository.updateFavorite(favorite)
    }

    fun getFavById(city:String) = viewModelScope.launch {
        repository.getFavById(city)
    }
}