package com.traum.dtos.booking

import com.traum.TimestampJson
import kotlinx.serialization.Serializable

@Serializable
class GetBookingDTO {
    var id: Long? = null
    var contactData: String? = null
    var start: TimestampJson? = null
    var end: TimestampJson? = null
    var isCanceled: Boolean? = null
    var tableId: Long? = null
}
