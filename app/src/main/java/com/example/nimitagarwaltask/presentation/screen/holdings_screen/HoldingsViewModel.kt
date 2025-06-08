package com.example.nimitagarwaltask.presentation.screen.holdings_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nimitagarwaltask.data.local.HoldingsEntity
import com.example.nimitagarwaltask.domain.usecase.IHoldingsUseCase
import com.example.nimitagarwaltask.utils.UiState

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HoldingsViewModel @Inject constructor(
    private val holdingsUseCase: IHoldingsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<HoldingsEntity>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<HoldingsEntity>>> = _uiState.asStateFlow()

    fun fetchHoldings() {
        viewModelScope.launch {
            holdingsUseCase.fetchHoldings().collect { state ->
                _uiState.value = state
            }
        }
    }
}

