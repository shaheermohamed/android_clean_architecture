package com.shahe.basiclearning.data.remote.dto

import com.shahe.basiclearning.domain.model.Article
import com.shahe.basiclearning.domain.model.News

data class NewsDto(
    val articles: List<Article>,
    val totalArticles: Int
)

fun NewsDto.toNews(): News {
    return News(
        articles = articles,
        totalArticles = totalArticles,
    )
}