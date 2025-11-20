package com.example.mycomposeapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Data классы для конфигурации
data class ColumnConfig(
    val title: String,
    val weight: Float,
    val fontSize: TextUnit = 14.sp
)

data class ColumnData(
    val text: String,
    val weight: Float,
    val fontSize: TextUnit = 14.sp,
    val isClickable: Boolean = false,
    val color: Color? = null,
    val onClick: (() -> Unit)? = null
)

@Composable
fun CommonTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.padding(8.dp).fillMaxWidth(),
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
    )
}

@Composable
fun CommonSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("$label:", modifier = Modifier.padding(end = 8.dp))
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}

@Composable
fun CommonActionButtons(
    isEditing: Boolean,
    onUpdate: () -> Unit,
    onCancel: () -> Unit,
    onAdd: () -> Unit,
    updateText: String = "Update",
    cancelText: String = "Cancel",
    addText: String = "Add"
) {
    Row(modifier = Modifier.padding(8.dp)) {
        if (isEditing) {
            Button(
                onClick = onUpdate,
                modifier = Modifier.weight(1f).padding(end = 4.dp)
            ) {
                Text(updateText, fontSize = 16.sp)
            }
            Button(
                onClick = onCancel,
                modifier = Modifier.weight(1f).padding(start = 4.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
            ) {
                Text(cancelText, fontSize = 16.sp)
            }
        } else {
            Button(
                onClick = onAdd,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(addText, fontSize = 16.sp)
            }
        }
    }
}

// Общие функции для списков
@Composable
fun <T> CommonList(
    items: List<T>,
    titleRow: @Composable () -> Unit,
    itemRow: @Composable (T) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier.fillMaxWidth().heightIn(max = 500.dp)) {
        item { titleRow() }
        items(items) { item -> itemRow(item) }
    }
}

@Composable
fun CommonTitleRow(
    columns: List<ColumnConfig>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(Color.LightGray)
            .fillMaxWidth()
            .padding(5.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        columns.forEach { column ->
            Text(
                text = column.title,
                color = Color.White,
                modifier = Modifier.weight(column.weight),
                fontSize = column.fontSize
            )
        }
    }
}

@Composable
fun CommonItemRow(
    columns: List<ColumnData>,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.White
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(5.dp)
            .background(backgroundColor),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        columns.forEach { column ->
            if (column.isClickable) {
                Text(
                    text = column.text,
                    modifier = Modifier
                        .weight(column.weight)
                        .clickable { column.onClick?.invoke() },
                    color = column.color ?: Color.Unspecified,
                    fontSize = column.fontSize
                )
            } else {
                Text(
                    text = column.text,
                    modifier = Modifier.weight(column.weight),
                    fontSize = column.fontSize
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonDropdown(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    options: List<String>,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier.padding(8.dp).fillMaxWidth()) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = value,
                onValueChange = { },
                modifier = Modifier.menuAnchor(),
                label = { Text(label) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                readOnly = true
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            onValueChange(option)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {

}