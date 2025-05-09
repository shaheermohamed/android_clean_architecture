package com.shahe.basiclearning.domain.use_case.get_News

import com.shahe.basiclearning.common.Resource
import com.shahe.basiclearning.data.remote.dto.toNews
import com.shahe.basiclearning.domain.model.News
import com.shahe.basiclearning.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    operator fun invoke(category: String): Flow<Resource<News>> = flow {
        try {
            emit(Resource.Loading<News>())
            val news = repository.getNews(category)
            emit(Resource.Success<News>(news.toNews()))
        } catch (e: HttpException) {
            emit(Resource.Error<News>(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error<News>("Couldn't reach server. Check your internet connection."))
        }
    }
}