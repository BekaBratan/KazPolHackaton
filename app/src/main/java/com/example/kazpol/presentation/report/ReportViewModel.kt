package com.example.kazpol.presentation.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kazpol.data.model.SignInRequest
import com.example.kazpol.data.model.SignInResponse
import com.example.kazpol.data.model.SignUpRequest
import com.example.kazpol.data.model.SignUpResponse
import com.example.unihub.data.api.ServiceBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReportViewModel: ViewModel() {
    private var _reportResponse: MutableLiveData<SignInResponse> = MutableLiveData()
    val reportResponse: MutableLiveData<SignInResponse> = _reportResponse

    private var _errorBody: MutableLiveData<String?> = MutableLiveData()
    val errorBody: LiveData<String?> = _errorBody

    fun report(password: String, phone: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching { ServiceBuilder.api.signIn(SignInRequest(password = password, phone = phone)) }.fold(
                onSuccess = {
                    _reportResponse.postValue(it)
                },
                onFailure = { throwable ->
                    _errorBody.postValue(throwable.message)
                }
            )
        }
    }
}