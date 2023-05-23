package com.traum.dtos.measure_of_item

import com.traum.BigDecimalJson
import kotlinx.serialization.Serializable

@Serializable
class PatchMeasureOfItemDTO {
    var amount: BigDecimalJson? = null
    var measurementId: Long? = null
}
