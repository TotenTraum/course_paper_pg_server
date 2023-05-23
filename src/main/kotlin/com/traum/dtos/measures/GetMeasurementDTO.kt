package com.traum.dtos.measures

import kotlinx.serialization.Serializable

@Serializable
class GetMeasurementDTO {
    var id: Long? = null
    var name: String? = null
}