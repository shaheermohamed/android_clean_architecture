package com.shahe.basiclearning.domain.repository

import com.shahe.basiclearning.data.remote.dto.NewsDto

interface NewsRepository {
    suspend fun getNews(category:String): NewsDto
}