package com.hardi.newsapp.ui.countryactivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hardi.newsapp.data.model.LocaleInfo
import com.hardi.newsapp.data.repository.CountriesRepository
import com.hardi.newsapp.ui.base.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class CountriesViewModel (private val countriesRepository: CountriesRepository) : ViewModel(){

    private val _uiState = MutableStateFlow<UiState<List<LocaleInfo>>>(UiState.Loading)

    val uiState: StateFlow<UiState<List<LocaleInfo>>> = _uiState

    init {
        fetchCountries()
    }

    private fun fetchCountries(){
        viewModelScope.launch {
            countriesRepository.getCountries()
                .catch { e ->
                    _uiState.value = UiState.Error(e.toString())
                }.collect(){
                    _uiState.value = UiState.Success(it)
                }
        }
    }
}