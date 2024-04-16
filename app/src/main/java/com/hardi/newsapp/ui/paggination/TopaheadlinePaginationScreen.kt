package me.hardi.newsapp.ui.paggination

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.hardi.newsapp.data.model.Article
import com.hardi.newsapp.ui.common.ArticleUI
import com.hardi.newsapp.ui.common.ShowError
import com.hardi.newsapp.ui.common.ShowLoading
import com.hardi.newsapp.ui.common.TopAppBarWithBackIconUI

@Composable
fun PaginationArticleRoute(
    onBackClick: () -> Unit,
    onNewsClick: (url: String) -> Unit,
    viewModel: PaginationViewmodel = hiltViewModel()
) {
    val article = viewModel.uiState.collectAsLazyPagingItems()

    Scaffold(topBar = {
        TopAppBarWithBackIconUI(
            title = "Pagination Article"
        ) {
            onBackClick()
        }
    }, content = { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
        ) {
            PaginationScreen(article, onNewsClick)
        }
    })
}

@Composable
fun PaginationScreen(articles: LazyPagingItems<Article>, onNewsClick: (url: String) -> Unit) {

    ArticleList(articles, onNewsClick)

    articles.apply {
        when {
            loadState.refresh is LoadState.Loading -> {
                ShowLoading()
            }

            loadState.refresh is LoadState.Error -> {
                val error = articles.loadState.refresh as LoadState.Error
                ShowError(error.error.localizedMessage!!)
            }

            loadState.append is LoadState.Loading -> {
                ShowLoading()
            }

            loadState.append is LoadState.Error -> {
                val error = articles.loadState.append as LoadState.Error
                ShowError(error.error.localizedMessage!!)
            }
        }
    }
}


@Composable
fun ArticleList(articles: LazyPagingItems<Article>, onNewsClick: (url: String) -> Unit) {
    LazyColumn {
        items(articles.itemCount, key = { index -> articles[index]!!.url }) { index ->
            ArticleUI(articles[index]!!, onNewsClick)
        }
    }
}
