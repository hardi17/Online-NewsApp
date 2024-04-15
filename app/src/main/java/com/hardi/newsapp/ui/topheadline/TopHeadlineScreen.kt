package com.hardi.newsapp.ui.topheadline

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
import com.hardi.newsapp.ui.base.UiState
import com.hardi.newsapp.ui.common.ShowError
import com.hardi.newsapp.ui.common.ShowLoading
import com.hardi.newsapp.ui.common.TopAppBarWithActionIconUI
import com.hardi.newsapp.ui.common.TopAppBarWithOutIconUI
import com.hardi.newsapp.ui.common.TopHeadlineUI
import me.hardi.newsapp.data.local.entity.ArticleEntity

@Composable
fun TopHeadlineroute(
    onNewsClick: (url: String) -> Unit,
    onPaginationClick: () -> Unit,
    viewModel: TopHeadlineViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBarWithActionIconUI(
                title = stringResource(id = R.string.top_headlines),
            ) {
                onPaginationClick()
            }
        }, content = { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues)
            ) {
                TopHeadlineScreen(uiState, onNewsClick)
            }

        }
    )
}

@Composable
fun TopHeadlineScreen(uiState: UiState<List<ArticleEntity>>, onNewsClick: (url: String) -> Unit) {
    when (uiState) {
        is UiState.Success -> {
            ArticleList(uiState.data, onNewsClick)
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
fun ArticleList(articles: List<ArticleEntity>, onNewsClick: (url: String) -> Unit) {
    LazyColumn {
        items(articles, key = { article -> article.url }) { article ->
            TopHeadlineUI(article, onNewsClick)
        }
    }
}


