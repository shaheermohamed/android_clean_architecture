package com.shahe.basiclearning.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.shahe.basiclearning.domain.model.Slip
import com.shahe.basiclearning.domain.model.SlipDetail

data class SlipDto(
    @SerializedName("slip")
    val slip: SlipDetail
)

fun SlipDto.toAdvice(): Slip {
    return Slip(
        id = slip.id,
        advice = slip.advice
    )
}