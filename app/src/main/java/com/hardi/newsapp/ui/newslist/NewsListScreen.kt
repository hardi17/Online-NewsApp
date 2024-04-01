package com.hardi.newsapp.ui.newslist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.hardi.newsapp.data.model.Article
import com.hardi.newsapp.ui.base.UiState
import com.hardi.newsapp.ui.reusable.ArticleUI
import com.hardi.newsapp.ui.reusable.ShowError
import com.hardi.newsapp.ui.reusable.ShowLoading

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsListRoute(
    onBakPress: () -> Unit,
    onNewsClick: (url: String) -> Unit,
    viewModel: NewsListViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                ),
                title = {
                    Text(
                        text = stringResource(id = R.string.news_list_by_selection)
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { onBakPress() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "null"
                        )
                    }
                })
        }, content = { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues)
            ) {
                NewsListScreen(uiState, onNewsClick)
            }
        }
    )
}

@Composable
fun NewsListScreen(uiState: UiState<List<Article>>, onNewsClick: (url: String) -> Unit) {
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
            ShowError(uiState.message)
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
