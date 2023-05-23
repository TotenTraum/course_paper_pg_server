package com.traum.dtos.price_of_item

import com.traum.BigDecimalJson
import com.traum.TimestampJson
import kotlinx.serialization.Serializable

@Serializable
class GetPriceOfItemDTO {
    var id: Long? = null
    var price: BigDecimalJson? = null
    var dateOfChange: TimestampJson? = null
    var itemId: Long? = null
}
