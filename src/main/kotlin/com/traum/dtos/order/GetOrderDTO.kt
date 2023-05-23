package com.traum.dtos.order

import com.traum.BigDecimalJson
import com.traum.TimestampJson
import kotlinx.serialization.Serializable

@Serializable
class GetOrderDTO {
    var id: Long? = null
    var tableId: Long? = null
    var employeeId: Long? = null
    var sum: BigDecimalJson? = null
    var date: TimestampJson? = null
}
