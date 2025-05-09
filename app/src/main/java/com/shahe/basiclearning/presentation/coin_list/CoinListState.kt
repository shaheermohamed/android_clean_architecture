package com.shahe.basiclearning.presentation.coin_list

import com.shahe.basiclearning.domain.model.Coin

data class CoinListState(
    val isLoading: Boolean = false,
    val coins: List<Coin> = emptyList(),
    val error: String = ""
)