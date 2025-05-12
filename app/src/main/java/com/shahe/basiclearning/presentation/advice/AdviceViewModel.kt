package com.shahe.basiclearning.presentation.advice

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahe.basiclearning.common.Resource
import com.shahe.basiclearning.domain.use_case.get_advice.GetAdviceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class AdviceViewModel @Inject constructor(
    private val getAdviceUseCase: GetAdviceUseCase
) : ViewModel() {
    var state by mutableStateOf(AdviceState())

    init {
        fetchAdvice()
    }

    fun fetchAdvice() {

        getAdviceUseCase.invoke().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    state = AdviceState(isLoading = true)
                }

                is Resource.Success -> {
                    println("getAdviceUseCase == ${result.data}")
                    state = AdviceState(advice = result.data)
                }

                is Resource.Error -> {
                    println("getAdviceUseCase == ${result.message}")
                    state = AdviceState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }


}