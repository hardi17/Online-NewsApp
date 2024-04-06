package com.hardi.newsapp.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import me.hardi.newsapp.data.local.entity.ArticleEntity
import me.hardi.newsapp.data.local.entity.SourceEntity

@Composable
fun TopHeadlineUI(article: ArticleEntity, onNewsClick: (url: String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                if (article.url.isNotEmpty()) {
                    onNewsClick(article.url)
                }
            }) {
        HeadlineBanner(article)
        HeadlineTitle(article.title)
        HeadlineDescription(article.description)
        HeadlineSource(article.source)
    }
}

@Composable
fun HeadlineSource(source: SourceEntity) {
    Text(
        text = source.name,
        style = MaterialTheme.typography.titleSmall,
        color = Color.Gray,
        maxLines = 2,
        modifier = Modifier.padding(
            start = 5.dp,
            end = 5.dp,
            top = 5.dp,
            bottom = 5.dp
        )
    )
}

@Composable
fun HeadlineDescription(description: String?) {
    if (!description.isNullOrEmpty()) {
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            maxLines = 2,
            modifier = Modifier.padding(5.dp)
        )
    }
}

@Composable
fun HeadlineTitle(title: String) {
    if (title.isNotEmpty()) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black,
            maxLines = 2,
            modifier = Modifier.padding(5.dp)
        )
    }
}

@Composable
fun HeadlineBanner(article: ArticleEntity) {
    AsyncImage(
        model = article.imageUrl,
        contentDescription = article.title,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth()
    )
}
