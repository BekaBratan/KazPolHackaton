package com.example.kazpol.presentation.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kazpol.data.model.NewsResponse
import com.example.kazpol.data.model.SignInRequest
import com.example.kazpol.data.model.SignInResponse
import com.example.kazpol.data.model.SignUpRequest
import com.example.kazpol.data.model.SignUpResponse
import com.example.unihub.data.api.ServiceBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthViewModel: ViewModel() {
    private var _signInResponse: MutableLiveData<SignInResponse> = MutableLiveData()
    val signInResponse: MutableLiveData<SignInResponse> = _signInResponse

    private var _signUpResponse: MutableLiveData<SignUpResponse> = MutableLiveData()
    val signUpResponse: MutableLiveData<SignUpResponse> = _signUpResponse

    private var _errorBody: MutableLiveData<String?> = MutableLiveData()
    val errorBody: LiveData<String?> = _errorBody

    fun signIn(password: String, phone: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching { ServiceBuilder.api.signIn(SignInRequest(password = password, phone = phone)) }.fold(
                onSuccess = {
                    _signInResponse.postValue(it)
                },
                onFailure = { throwable ->
                    _errorBody.postValue(throwable.message)
                }
            )
        }
    }

    fun signUp(currentLocation: String, dateOfBirth: String, iin: String, name: String, password: String, phone: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                ServiceBuilder.api.signUp(
                    SignUpRequest(
                        currentLocation = currentLocation, dateOfBirth = dateOfBirth, iin = iin, name = name, password = password, phone = phone,
                        )
                )
            }.fold(
                onSuccess = {
                    _signUpResponse.postValue(it)
                },
                onFailure = { throwable ->
                    _errorBody.postValue(throwable.message)
                }
            )
        }
    }
}