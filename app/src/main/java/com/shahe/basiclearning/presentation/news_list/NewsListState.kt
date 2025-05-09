package com.shahe.basiclearning.presentation.news_list

import com.shahe.basiclearning.domain.model.News

data class NewsListState(
    val isLoading: Boolean = false,
    val news: News? = News(emptyList(), 0),
    val error: String = ""
)

