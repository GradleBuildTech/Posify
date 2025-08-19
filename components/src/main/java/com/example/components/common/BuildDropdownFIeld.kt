package com.example.components.common
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.core.lib.constants.DesignSystem

/**
 * BuildAppDropdownField is a composable function that creates a customizable dropdown field.
 * It allows users to select an item from a list, with a hint and optional prefix icon.
 * @param modifier Modifier to be applied to the dropdown field.
 * @param value The currently selected value, or null if no value is selected.
 * @param items The list of items to display in the dropdown.
 * @param onValueChange Callback invoked when a new value is selected.
 * @param hint Hint text displayed when no value is selected.
 * @param prefixIcon Optional icon displayed before the hint text.
 * @param borderColor Optional color for the border; if null, defaults to primary color.
 * @param borderWidth Width of the border around the dropdown field.
 * @param cornerRadius Corner radius for the dropdown field's border.
 * @param textColor Color of the text when a value is selected.
 * @param hintColor Color of the hint text when no value is selected.
 */
@Composable
fun <T> BuildAppDropdownField(
    modifier: Modifier = Modifier,
    dropdownModifier: Modifier = Modifier,
    value: T?,
    items: List<T>,
    onValueChange: (T) -> Unit,
    hint: String,
    textStyle: TextStyle? = null,
    prefixIcon: ImageVector = Icons.Default.Home,
    borderColor: Color? = null,
    borderWidth: Dp = 1.5.dp,
    cornerRadius: Dp = 8.dp, // Match TextField radius
    textColor: Color = Color.Black,
    hintColor: Color = Color.Gray,
    itemLabel: (T) -> String = { it.toString() }
) {
    var expanded by remember { mutableStateOf(false) }
    val resolvedBorderColor = borderColor ?: MaterialTheme.colorScheme.primary

    Box(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = borderWidth,
                color = resolvedBorderColor,
                shape = RoundedCornerShape(cornerRadius)
            )
            .clickable { expanded = !expanded }
            .padding(horizontal = 12.dp, vertical = 10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = prefixIcon,
                contentDescription = null,
                tint = resolvedBorderColor,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = value?.let(itemLabel) ?: hint,
                style = (textStyle ?: DesignSystem.TITLE_SMALL_STYLE).copy(color = if (value == null) hintColor else textColor),
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                tint = resolvedBorderColor
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = dropdownModifier.fillMaxWidth()
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(itemLabel(item), style =  textStyle ?: DesignSystem.TITLE_SMALL_STYLE) },
                    onClick = {
                        onValueChange(item)
                        expanded = false
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
    var selectedItem by remember { mutableStateOf<String?>(null) }

    BuildAppDropdownField(
        value = selectedItem,
        items = items,
        onValueChange = { selectedItem = it },
        hint = "Select an item",
        prefixIcon = Icons.Default.Home,
        borderColor = Color.Blue,
        textColor = Color.Black,
        hintColor = Color.Gray
    )
}
