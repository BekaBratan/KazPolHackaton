package com.example.kazpol.presentation.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kazpol.data.NewsResponse
import com.example.unihub.data.api.ServiceBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel: ViewModel() {

    private var _newsResponse: MutableLiveData<NewsResponse> = MutableLiveData()
    val newsResponse: MutableLiveData<NewsResponse> = _newsResponse

    private var _errorBody: MutableLiveData<String?> = MutableLiveData()
    val errorBody: LiveData<String?> = _errorBody

    fun getNews() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching { ServiceBuilder.apiNews.getTopHeadlinesBySource("bbc-news", "e602d1ee05a64de19b52af47ad02ef97") }.fold(
                onSuccess = {
                    _newsResponse.postValue(it)
                },
                onFailure = { throwable ->
                    _errorBody.postValue(throwable.message)
                }
            )
        }
    }
}