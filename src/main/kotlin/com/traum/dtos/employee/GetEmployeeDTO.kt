package com.traum.dtos.employee

import kotlinx.serialization.Serializable

@Serializable
class GetEmployeeDTO {
    var id: Long? = null
    var name: String? = null
    var phoneNumber: String? = null
}