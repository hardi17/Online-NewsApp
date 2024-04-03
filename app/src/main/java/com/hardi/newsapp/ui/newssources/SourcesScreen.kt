package com.hardi.newsapp.ui.newssources

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
import com.hardi.newsapp.data.model.NewsSource
import com.hardi.newsapp.ui.base.UiState
import com.hardi.newsapp.ui.common.ShowError
import com.hardi.newsapp.ui.common.ShowLoading
import com.hardi.newsapp.ui.common.SourceUI
import com.hardi.newsapp.ui.common.TopAppBarWithOutIconUI

@Composable
fun NewsSourceRoute(
    onSourceClick: (sourceId: String) -> Unit,
    viewModel: SourcesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBarWithOutIconUI(title = stringResource(id = R.string.new_sources))
        }, content = { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues)
            ) {
                NewsSourceScreen(uiState, onSourceClick)
            }
        })
}

@Composable
fun NewsSourceScreen(
    uiState: UiState<List<NewsSource>>,
    onSourceClick: (sourceId: String) -> Unit
) {
    when (uiState) {
        is UiState.Success -> {
            NewsSourceList(uiState.data, onSourceClick)
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
fun NewsSourceList(sources: List<NewsSource>, onSourceClick: (sourceId: String) -> Unit) {
    LazyColumn {
        items(sources, key = { source -> source.id!! }) { source ->
            SourceUI(source, onSourceClick)
        }
    }
}


