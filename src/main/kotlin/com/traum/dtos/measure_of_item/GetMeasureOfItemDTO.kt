package com.traum.dtos.measure_of_item

import com.traum.BigDecimalJson
import kotlinx.serialization.Serializable

@Serializable
class GetMeasureOfItemDTO {
    var id: Long? = null
    var amount: BigDecimalJson? = null
    var itemId: Long? = null
    var measurementId: Long? = null
}
