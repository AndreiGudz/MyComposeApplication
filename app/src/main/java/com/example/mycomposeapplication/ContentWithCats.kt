package com.example.mycomposeapplication

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import com.example.mycomposeapplication.practic5.Cat
import com.example.mycomposeapplication.practic5.CatViewModel

@Composable
fun ContentWithCats(vm: CatViewModel) {
    val catList by vm.catsList.observeAsState(listOf())
    val ownersList by vm.ownersList.observeAsState(listOf())
    val scrollState = rememberScrollState()

    Column(Modifier.verticalScroll(scrollState)) {
        CommonTextField(
            value = vm.catName,
            onValueChange = { vm.changeName(it) },
            label = "Cat Name"
        )

        CommonTextField(
            value = vm.catBreed,
            onValueChange = { vm.changeBreed(it) },
            label = "Breed"
        )

        CommonTextField(
            value = vm.catAge,
            onValueChange = { vm.changeAge(it) },
            label = "Age",
            keyboardType = KeyboardType.Number
        )

        CommonDropdown(
            value = if (vm.catOwnerId.isNotEmpty()) {
                val ownerId = vm.catOwnerId.toLongOrNull()
                if (ownerId != null)
                    vm.getOwnerNameById(ownerId)
                else
                    "No Owner"
            }
            else
                "No Owner",
            onValueChange = { selectedOwnerName ->
                if (selectedOwnerName == "No Owner") {
                    vm.changeOwnerId("")
                } else {
                    val selectedOwner = ownersList.find { it.name == selectedOwnerName }
                    selectedOwner?.let { owner ->
                        vm.changeOwnerId(owner.id.toString())
                    }
                }
            },
            label = "Owner",
            options = listOf("No Owner") + ownersList.map { it.name }
        )

        CommonSwitch(
            checked = vm.likeCatnip,
            onCheckedChange = { vm.changeLikeCatnip(it) },
            label = "Likes Catnip"
        )

        CommonActionButtons(
            isEditing = vm.editingCatId != null,
            onUpdate = { vm.editingCatId?.let { id -> vm.updateCat(id) } },
            onCancel = { vm.cancelEditing() },
            onAdd = { vm.addCat() },
            updateText = "Update Cat",
            addText = "Add Cat"
        )

        CatList(
            cats = catList,
            onEdit = { vm.startEditing(it) },
            delete = { vm.deleteCat(it) }
        )
    }
}

@Composable
fun CatList(cats: List<Cat>, onEdit: (Cat) -> Unit, delete: (Long) -> Unit) {
    val catColumns = listOf(
        ColumnConfig("Id", 0.5f),
        ColumnConfig("Name", 1.5f),
        ColumnConfig("Breed", 1.5f),
        ColumnConfig("Age", 1f),
        ColumnConfig("Likes catnip", 1f),
        ColumnConfig("Owner ID", 1f),
        ColumnConfig("Edit", 1f),
        ColumnConfig("Delete", 1f)
    )

    CommonList(
        items = cats,
        titleRow = { CommonTitleRow(columns = catColumns) },
        itemRow = { cat ->
            val catData = listOf(
                ColumnData(cat.id.toString(), 0.5f),
                ColumnData(cat.name, 1.5f),
                ColumnData(cat.breed, 1.5f),
                ColumnData(cat.age.toString(), 1f),
                ColumnData(if (cat.likeCatnip) "Yes" else "No", 1f),
                ColumnData(cat.ownerId?.toString() ?: "None", 1f),
                ColumnData(
                    text = "Edit",
                    weight = 1f,
                    isClickable = true,
                    color = Color(0xFF2196F3),
                    onClick = { onEdit(cat) }
                ),
                ColumnData(
                    text = "Delete",
                    weight = 1f,
                    isClickable = true,
                    color = Color(0xFFF44336),
                    onClick = { delete(cat.id) }
                )
            )
            CommonItemRow(columns = catData)
        }
    )
}