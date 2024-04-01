package com.hardi.newsapp.ui.searchactivity

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hardi.newsapp.R
import com.hardi.newsapp.data.model.Article
import com.hardi.newsapp.ui.base.UiState
import com.hardi.newsapp.ui.reusable.ArticleUI
import com.hardi.newsapp.ui.reusable.ShowDefaultMessage
import com.hardi.newsapp.ui.reusable.ShowError
import com.hardi.newsapp.ui.reusable.ShowLoading

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchNewsRoute(
    onBakPress: () -> Unit,
    onSearchNewsClick: (url: String) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val queryState: String by viewModel.query.collectAsStateWithLifecycle()

    Scaffold(topBar = {
        TopAppBar(
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = Color.White,
                navigationIconContentColor = Color.White
            ),
            title = {
                Text(
                    text = stringResource(id = R.string.search)
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
            SearchNewsScreen(
                searchUIState = uiState,
                searchQuery = queryState,
                onSearchNewsClick = onSearchNewsClick,
                viewModel = viewModel
            )
        }
    }
    )

}

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchNewsScreen(
    searchUIState: UiState<List<Article>>,
    searchQuery: String,
    onSearchNewsClick: (url: String) -> Unit,
    viewModel: SearchViewModel
) {
    SearchBar(
        query = searchQuery,
        onQueryChange = { viewModel.searchNews(it) },
        onSearch = {},
        placeholder = {
            Text(text = stringResource(id = R.string.search_here))
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        },
        active = true,
        onActiveChange = {},
        tonalElevation = 2.dp
    ) {
        if (searchQuery.isEmpty()) {
            ShowDefaultMessage(stringResource(id = R.string.no_results))
        } else {
            SearchNews(searchUIState, onSearchNewsClick)
        }
    }
}

@Composable
fun SearchNews(
    uiState: UiState<List<Article>>,
    onSearchNewsClick: (url: String) -> Unit
) {
    when (uiState) {
        is UiState.Success -> {
            if (uiState.data.isEmpty()) {
                ShowError(stringResource(id = R.string.no_news_found))
            } else {
                SearchNewsList(uiState.data, onSearchNewsClick)
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
fun SearchNewsList(articles: List<Article>, onNewsClick: (url: String) -> Unit) {
    LazyColumn {
        items(articles, key = { article -> article.url }) { article ->
            ArticleUI(article, onNewsClick)
        }
    }
}
