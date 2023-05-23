package com.traum.dtos.element_of_order

import com.traum.BigDecimalJson
import kotlinx.serialization.Serializable

@Serializable
class GetElementOfOrderDTO {
    var id: Long? = null
    var amount: Int? = null
    var sum: BigDecimalJson? = null
    var priceOfItemId: Long? = null
    var orderId: Long? = null
    var itemId: Long? = null
}
