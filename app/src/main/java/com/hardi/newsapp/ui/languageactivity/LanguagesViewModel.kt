package com.hardi.newsapp.ui.languageactivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hardi.newsapp.data.model.LocaleInfo
import com.hardi.newsapp.data.repository.LanguagesRepository
import com.hardi.newsapp.ui.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LanguagesViewModel @Inject constructor(private val languagesRepository: LanguagesRepository): ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<LocaleInfo>>>(UiState.Loading)

    val uiState: StateFlow<UiState<List<LocaleInfo>>> = _uiState

    init {
        fetchLanguages()
    }

    private fun fetchLanguages(){
        viewModelScope.launch {
            languagesRepository.getLanguages()
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    _uiState.value = UiState.Error(e.toString())
                }.collect(){
                    _uiState.value = UiState.Success(it)
                }
        }
    }
}