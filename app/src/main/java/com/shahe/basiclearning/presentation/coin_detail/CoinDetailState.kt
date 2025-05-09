package com.shahe.basiclearning.presentation.coin_detail

import com.shahe.basiclearning.domain.model.CoinDetail

data class CoinDetailState(
    val isLoading: Boolean = false,
    val coin: CoinDetail? = null,
    val error: String = ""
)
