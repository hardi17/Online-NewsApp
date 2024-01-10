package com.hardi.newsapp.ui.languageactivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hardi.newsapp.data.model.CountryOrLanguage
import com.hardi.newsapp.data.repository.LanguagesRepository
import com.hardi.newsapp.ui.base.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class LanguagesViewModel(private val languagesRepository: LanguagesRepository): ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<CountryOrLanguage>>>(UiState.Loading)

    val uiState: StateFlow<UiState<List<CountryOrLanguage>>> = _uiState

    init {
        fetchLanguages()
    }

    private fun fetchLanguages(){
        viewModelScope.launch {
            languagesRepository.getLanguages()
                .catch { e ->
                    _uiState.value = UiState.Error(e.toString())
                }.collect(){
                    _uiState.value = UiState.Success(it)
                }
        }
    }
}