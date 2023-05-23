package com.traum.dtos.booking

import com.traum.TimestampJson
import kotlinx.serialization.Serializable

@Serializable
data class PostBookingDTO(
    var contactData: String,
    var start: TimestampJson,
    var tableId: Long,
)