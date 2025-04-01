package com.example.kazpol.presentation.sos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kazpol.data.model.ErrorResponse
import com.example.kazpol.data.model.SOSRequest
import com.example.kazpol.data.model.SignInRequest
import com.example.kazpol.data.model.SignInResponse
import com.example.kazpol.data.model.SignUpResponse
import com.example.unihub.data.api.ServiceBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class SOSViewModel: ViewModel() {
    private var _sosResponse: MutableLiveData<SignUpResponse> = MutableLiveData()
    val sosResponse: MutableLiveData<SignUpResponse> = _sosResponse

    private var _errorResponse: MutableLiveData<ErrorResponse?> = MutableLiveData()
    val errorResponse: MutableLiveData<ErrorResponse?> = _errorResponse

    private var _errorBody: MutableLiveData<String?> = MutableLiveData()
    val errorBody: LiveData<String?> = _errorBody

    fun SOS(token: String, longitude: Int, latitude: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching { ServiceBuilder.api.SOS(token = token, SOSRequest(longitude = longitude, latitude = latitude)) }.fold(
                onSuccess = {
                    _sosResponse.postValue(it)
                },
                onFailure = { throwable ->
                    if (throwable is HttpException) {
                        val gson = com.google.gson.Gson()
                        val errorBody = throwable.response()?.errorBody()?.string()
                        val errorResponse = gson.fromJson(errorBody, ErrorResponse::class.java)
                        _errorResponse.postValue(errorResponse)
                    } else {
                        _errorBody.postValue(throwable.message)
                    }
                }
            )
        }
    }
}