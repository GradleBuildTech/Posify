package com.example.core.models.request.floor

import com.example.core.models.enum.YesNoCheck

data class GetFloorRequest(
    val page: Int? = null,
    val pageSize: Int? = null,
    val name: String? = null,
    val isActive: YesNoCheck? = null,
    val orgId: Int = 0,
    val posTerminalId: Int? = null
) {
    fun toMap(): Map<String, String> {
        val result = mutableMapOf<String, String>()
        if (page != null && pageSize != null) {
            result["page"] = page.toString()
            result["pageSize"] = pageSize.toString()
        }
        if (!name.isNullOrEmpty()) {
            result["name"] = name
        }
        if (isActive != null && isActive != YesNoCheck.ALL) {
            result["isActive"] = isActive.value
        }
        result["orgId"] = orgId.toString()
        if (posTerminalId != null) {
            result["posTerminalId"] = posTerminalId.toString()
        }
        return result
    }
}
