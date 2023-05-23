package com.traum.dtos.log

import com.traum.TimestampJson
import kotlinx.serialization.Serializable

@Serializable
class GetLogDTO {
    var id: Long? = null
    var source: String? = null
    var created: TimestampJson? = null
    var roleCreated: String? = null
}