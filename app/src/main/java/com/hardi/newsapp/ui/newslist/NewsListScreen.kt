package com.hardi.newsapp.ui.newslist

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
import com.hardi.newsapp.data.model.Article
import com.hardi.newsapp.ui.base.UiState
import com.hardi.newsapp.ui.common.ArticleUI
import com.hardi.newsapp.ui.common.ShowError
import com.hardi.newsapp.ui.common.ShowErrorView
import com.hardi.newsapp.ui.common.ShowLoading
import com.hardi.newsapp.ui.common.TopAppBarWithBackIconUI
import com.hardi.newsapp.utils.NoInternetException

@Composable
fun NewsListRoute(
    onBackPress: () -> Unit,
    onNewsClick: (url: String) -> Unit,
    viewModel: NewsListViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBarWithBackIconUI(
                title = stringResource(id = R.string.news_list_by_selection)
            ) {
                onBackPress()
            }
        }, content = { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues)
            ) {
                NewsListScreen(uiState, onNewsClick, viewModel)
            }
        }
    )
}

@Composable
fun NewsListScreen(
    uiState: UiState<List<Article>>,
    onNewsClick: (url: String) -> Unit,
    viewModel: NewsListViewModel
) {
    when (uiState) {
        is UiState.Success -> {
            if (uiState.data.isEmpty()) {
                ShowError(stringResource(id = R.string.no_data_found_fitler))
            } else {
                NewsList(uiState.data, onNewsClick)
            }
        }

        is UiState.Loading -> {
            ShowLoading()
        }

        is UiState.Error -> {
            var errorText = stringResource(id = R.string.something_went_wrong)
            if (uiState.throwable is NoInternetException) {
                errorText = stringResource(id = R.string.network_error)
            }
            ShowErrorView(
                text = errorText
            ) {
                viewModel.fetchNewsByfilter()
            }
        }
    }
}


@Composable
fun NewsList(articles: List<Article>, onNewsClick: (url: String) -> Unit) {
    LazyColumn {
        items(articles, key = { article -> article.url }) { article ->
            ArticleUI(article, onNewsClick)
        }
    }
}
