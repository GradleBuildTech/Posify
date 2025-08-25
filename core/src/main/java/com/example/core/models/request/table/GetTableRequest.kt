package com.example.core.models.request.table

import com.example.core.models.enum.TableStatus
import com.example.core.models.enum.YesNoCheck

data class GetTableRequest(
    val page: Int? = null,
    val pageSize: Int? = null,
    val name: String? = null,
    val isActive: YesNoCheck? = null,
    val floorId: Int? = null,
    val orgId: Int = 0,
    val reservationDate: String? = null,
    val isActiveTable: TableStatus? = null,
    val posTerminalId: Int? = null,
    val tableId: Int? = null,
    val isDefault: YesNoCheck? = null
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
        if (isActiveTable != null && isActiveTable != TableStatus.ALL) {
            result["tableStatus"] = isActiveTable.value
        }
        if (floorId != null && floorId != 0) {
            result["floorId"] = floorId.toString()
        }
        if (posTerminalId != null) {
            result["posTerminalId"] = posTerminalId.toString()
        }
        if (tableId != null) {
            result["tableId"] = tableId.toString()
        }
        if (isDefault != null && isDefault != YesNoCheck.ALL) {
            result["isDefault"] = isDefault.value.toString()
        }

        if (!reservationDate.isNullOrEmpty()) {
            result["reservationDate"] = reservationDate
        }
        result["orgId"] = orgId.toString()
        return result
    }
}