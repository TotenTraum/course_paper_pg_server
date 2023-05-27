package com.traum.dtos.order

import com.traum.BigDecimalJson
import com.traum.TimestampJson
import kotlinx.serialization.Serializable

@Serializable
data class PostOrderDTO(
    val tableId: Long,
    val employeeId: Long
)
