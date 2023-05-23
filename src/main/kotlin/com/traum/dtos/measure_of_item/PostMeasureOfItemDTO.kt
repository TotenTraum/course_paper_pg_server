package com.traum.dtos.measure_of_item

import com.traum.BigDecimalJson
import kotlinx.serialization.Serializable

@Serializable
class PostMeasureOfItemDTO(
    var amount: BigDecimalJson,
    var measurementId: Long
)
