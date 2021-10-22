package com.gvelesiani.movieapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gvelesiani.movieapp.base.BaseViewModel
import com.gvelesiani.movieapp.domain.useCases.GetWelcomeScreenButtonStateUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainViewModel(private val getWelcomeScreenButtonStateUseCase: GetWelcomeScreenButtonStateUseCase) :
    BaseViewModel() {

    private val _isClicked: MutableLiveData<Boolean> = MutableLiveData()
    val isClicked: LiveData<Boolean>
        get() {
            return _isClicked
        }


    init {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getWelcomeScreenButtonStateUseCase.doWork(Unit)
            withContext(Dispatchers.Main) {
                _isClicked.value = result
            }
        }
    }
}