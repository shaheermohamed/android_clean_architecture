package com.shahe.basiclearning.data.repository

import com.shahe.basiclearning.common.Constants
import com.shahe.basiclearning.data.remote.dto.GNewsApi
import com.shahe.basiclearning.data.remote.dto.NewsDto
import com.shahe.basiclearning.domain.repository.NewsRepository
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(private val api: GNewsApi) : NewsRepository {
    override suspend fun getNews(category: String): NewsDto {
        return api.getNews(category = category, apiKey = Constants.NEWS_API_KEY)
    }
}