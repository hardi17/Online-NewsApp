package com.hardi.newsapp.ui.language

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hardi.newsapp.R
import com.hardi.newsapp.data.model.LocaleInfo
import com.hardi.newsapp.ui.base.UiState
import com.hardi.newsapp.ui.common.LanguageUI
import com.hardi.newsapp.ui.common.ShowError
import com.hardi.newsapp.ui.common.ShowLoading
import com.hardi.newsapp.ui.common.TopAppBarWithOutIconUI

@Composable
fun LanguageListRoute(
    onLanguageClick: (langId: String) -> Unit,
    viewModel: LanguagesViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBarWithOutIconUI(title = stringResource(id = R.string.languages))
        }, content = { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues)
            ) {
                LanguageListScreen(uiState, onLanguageClick)
            }

        }
    )
}

@Composable
fun LanguageListScreen(
    uiState: UiState<List<LocaleInfo>>,
    onLanguageClick: (langId: String) -> Unit
) {
    when (uiState) {
        is UiState.Success -> {
            Languages(uiState.data, onLanguageClick)
        }

        is UiState.Loading -> {
            ShowLoading()
        }

        is UiState.Error -> {
            ShowError(uiState.message.toString())
        }

    }
}

@Composable
fun Languages(languages: List<LocaleInfo>, onLanguageClick: (langId: String) -> Unit) {
    LazyColumn {
        items(languages, key = { language -> language.code }) { language ->
            LanguageUI(language, onLanguageClick)
        }
    }

}

