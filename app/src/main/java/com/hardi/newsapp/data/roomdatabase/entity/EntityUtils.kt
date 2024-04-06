package com.hardi.newsapp.data.roomdatabase.entity

import com.hardi.newsapp.data.model.Article
import com.hardi.newsapp.data.model.Source
import me.hardi.newsapp.data.local.entity.ArticleEntity
import me.hardi.newsapp.data.local.entity.SourceEntity

fun Article.toArticleEntity(): ArticleEntity {
    return ArticleEntity(
        title = title,
        description = description,
        url = url,
        imageUrl = imageUrl,
        source = source.toSourceEntity()
    )
}

fun Source.toSourceEntity(): SourceEntity {
    return SourceEntity(
        id = id,
        name = name
    )
}

