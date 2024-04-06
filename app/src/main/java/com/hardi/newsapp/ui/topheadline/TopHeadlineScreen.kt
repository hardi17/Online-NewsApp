package com.hardi.newsapp.ui.topheadline

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.hardi.newsapp.R
import com.hardi.newsapp.data.model.Article
import com.hardi.newsapp.ui.base.UiState
import com.hardi.newsapp.ui.common.ArticleUI
import com.hardi.newsapp.ui.common.ShowError
import com.hardi.newsapp.ui.common.ShowLoading
import com.hardi.newsapp.ui.common.TopAppBarWithOutIconUI
import com.hardi.newsapp.ui.common.TopHeadlineUI
import me.hardi.newsapp.data.local.entity.ArticleEntity
import me.hardi.newsapp.data.local.entity.SourceEntity

@Composable
fun TopHeadlineroute(
    onNewsClick: (url: String) -> Unit,
    viewModel: TopHeadlineViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBarWithOutIconUI(title = stringResource(id = R.string.top_headlines))
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


