package com.shahe.basiclearning.presentation.news_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahe.basiclearning.common.Resource
import com.shahe.basiclearning.domain.model.News
import com.shahe.basiclearning.domain.use_case.get_News.GetNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase
) : ViewModel() {
    private val _state = mutableStateOf(
        NewsListState(
            isLoading = false,
            error = "",
            news = News(emptyList(), 0),
        )
    )
    val state: State<NewsListState> = _state
    private val _selectedCategory = mutableStateOf("general")
    val selectedCategory: State<String> = _selectedCategory
    init {
        getNews(category = selectedCategory.toString())
    }
    fun onCategorySelected(category: String) {
        if (category != _selectedCategory.value) {
            _selectedCategory.value = category
           getNews(category)
        }
    }
    private fun getNews(category: String) {
        getNewsUseCase.invoke(category).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = NewsListState(news = result.data ?: News(emptyList(), 0))
                    println("result.totalArticles==${result.data?.totalArticles}result.data==${result.data?.articles?.size}")
                }

                is Resource.Error -> {
                    _state.value = NewsListState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }

                is Resource.Loading -> {
                    _state.value = NewsListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}