package com.traum.dtos.price_of_item

import com.traum.BigDecimalJson
import kotlinx.serialization.Serializable

@Serializable
data class PostPriceOfItemDTO(
    var price: BigDecimalJson
)
