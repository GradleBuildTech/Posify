package com.example.components.common

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun <T> BuildAppDropdownField(
    modifier: Modifier = Modifier,
    value: T?,
    items: List<T>,
    onValueChange: (T) -> Unit,
    hint: String,
    prefixIcon: ImageVector = Icons.Default.Home, // default icon
    borderColor:  Color? = null,
    borderWidth: Dp = 1.5.dp,
    cornerRadius: Dp = 18.dp,
    textColor: Color = Color.Black,
    hintColor: Color = Color.Gray,
    isExpanded: Boolean = true,
    itemLabel: (T) -> String = { it.toString() }
) {
    var expanded = remember { mutableStateOf(false) }

    val borderColor = borderColor ?: MaterialTheme.colorScheme.primary
    Box(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = borderWidth,
                color = borderColor,
                shape = RoundedCornerShape(cornerRadius)
            )
            .clickable { expanded.value = !expanded.value }
            .padding(horizontal = 12.dp, vertical = 10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                prefixIcon,
                contentDescription = null,
                tint = borderColor,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = value?.let { itemLabel(it) } ?: hint,
                style = TextStyle(),
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = if (expanded.value) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                tint = borderColor
            )
        }

        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(itemLabel(item)) },
                    onClick = {
                        onValueChange(item)
                        expanded.value = false
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewBuildAppDropdownField() {
    val items = listOf("Item 1", "Item 2", "Item 3")
    var selectedItem = remember { mutableStateOf<String?>(null) }

    BuildAppDropdownField(
        value = selectedItem.value,
        items = items,
        onValueChange = { selectedItem.value = it },
        hint = "Select an item",
        prefixIcon = Icons.Default.Home,
        borderColor = Color.Blue,
        textColor = Color.Black,
        hintColor = Color.Gray
    )
}