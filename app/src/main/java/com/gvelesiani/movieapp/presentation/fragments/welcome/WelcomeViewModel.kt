package com.gvelesiani.movieapp.presentation.fragments.welcome

import androidx.lifecycle.viewModelScope
import com.gvelesiani.movieapp.base.BaseViewModel
import com.gvelesiani.movieapp.domain.useCases.UpdateWelcomeScreenButtonStateUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WelcomeViewModel(private val updateWelcomeScreenButtonStateUseCase: UpdateWelcomeScreenButtonStateUseCase) :
    BaseViewModel() {

    fun updateWelcomeScreenButtonStateUseCase(isClicked: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            updateWelcomeScreenButtonStateUseCase.run(isClicked)
        }
    }
}