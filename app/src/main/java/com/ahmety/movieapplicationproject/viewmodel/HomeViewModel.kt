package com.ahmety.movieapplicationproject.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmety.movieapplicationproject.model.Movie
import com.ahmety.movieapplicationproject.repository.HomeRepository
import com.ahmety.movieapplicationproject.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {

    val movieList: MutableLiveData<Resource<Movie?>> = MutableLiveData()

    fun getMovie(movieName: String){
        movieList.postValue(Resource.Loading())
        viewModelScope.launch {
            try {
                    val response = homeRepository.getMovie(movieName,"ffe9063f")
                    movieList.postValue(Resource.Success(response.body()))
            } catch (ex: Exception) {
                when (ex) {
                    is IOException -> movieList.postValue(Resource.Error("Network Failure " +  ex.localizedMessage))
                    else -> movieList.postValue(Resource.Error("Conversion Error"))
                }
            }
        }
    }

}