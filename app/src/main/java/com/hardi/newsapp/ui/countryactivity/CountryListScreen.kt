package com.hardi.newsapp.ui.countryactivity

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hardi.newsapp.R
import com.hardi.newsapp.data.model.LocaleInfo
import com.hardi.newsapp.ui.base.UiState
import com.hardi.newsapp.ui.reusable.CountryUI
import com.hardi.newsapp.ui.reusable.ShowError
import com.hardi.newsapp.ui.reusable.ShowLoading

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryListRoute(
    onCountryClick: (countryId: String) -> Unit,
    viewModel: CountriesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                ),
                title = {
                    Text(
                        text = stringResource(id = R.string.countries)
                    )
                })
        }, content = { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues)
            ) {
                CountryListScreen(uiState, onCountryClick)
            }
        }
    )
}

@Composable
fun CountryListScreen(
    uiState: UiState<List<LocaleInfo>>,
    onCountryClick: (countryId: String) -> Unit
) {

    when (uiState) {
        is UiState.Success -> {
            Countries(uiState.data, onCountryClick)
        }

        is UiState.Loading -> {
            ShowLoading()
        }

        is UiState.Error -> {
            ShowError(uiState.message)
        }

    }
}

@Composable
fun Countries(countries: List<LocaleInfo>, onCountryClick: (countryId: String) -> Unit) {
    LazyColumn {
        items(countries, key = { country -> country.code }) { country ->
            CountryUI(country, onCountryClick)
        }
    }
}


