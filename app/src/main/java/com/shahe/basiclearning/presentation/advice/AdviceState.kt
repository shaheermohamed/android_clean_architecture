package com.shahe.basiclearning.presentation.advice

import com.shahe.basiclearning.domain.model.Slip

data class AdviceState(
    val isLoading: Boolean = false,
    val advice: Slip? = Slip(
        advice = "", id = 0
    ),
    val error: String = ""
)

