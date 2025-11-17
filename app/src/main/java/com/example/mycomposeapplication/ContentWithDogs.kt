package com.example.mycomposeapplication

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import com.example.mycomposeapplication.practic5.Dog
import com.example.mycomposeapplication.practic5.DogViewModel

@Composable
fun ContentWithDogs(vm: DogViewModel) {
    val dogList by vm.dogsList.observeAsState(listOf())
    val scrollState = rememberScrollState()

    Column(Modifier.verticalScroll(scrollState)) {
        // Форма добавления/редактирования собаки
        CommonTextField(
            value = vm.dogName,
            onValueChange = { vm.changeName(it) },
            label = "Dog Name"
        )

        CommonTextField(
            value = vm.dogBreed,
            onValueChange = { vm.changeBreed(it) },
            label = "Breed"
        )

        CommonTextField(
            value = vm.dogAge,
            onValueChange = { vm.changeAge(it) },
            label = "Age",
            keyboardType = KeyboardType.Number
        )

        CommonTextField(
            value = vm.dogSize,
            onValueChange = { vm.changeSize(it) },
            label = "Size (Small/Medium/Large)"
        )

        CommonTextField(
            value = vm.dogOwnerId,
            onValueChange = { vm.changeOwnerId(it) },
            label = "Owner ID (optional)",
            keyboardType = KeyboardType.Number
        )

        CommonSwitch(
            checked = vm.isTrained,
            onCheckedChange = { vm.changeIsTrained(it) },
            label = "Is Trained"
        )

        CommonActionButtons(
            isEditing = vm.editingDogId != null,
            onUpdate = { vm.editingDogId?.let { id -> vm.updateDog(id) } },
            onCancel = { vm.cancelEditing() },
            onAdd = { vm.addDog() },
            updateText = "Update Dog",
            addText = "Add Dog"
        )

        DogList(
            dogs = dogList,
            onEdit = { vm.startEditing(it) },
            delete = { vm.deleteDog(it) }
        )
    }
}

@Composable
fun DogList(dogs: List<Dog>, onEdit: (Dog) -> Unit, delete: (Long) -> Unit) {
    val dogColumns = listOf(
        ColumnConfig("Id", 1f),
        ColumnConfig("Name", 2f),
        ColumnConfig("Breed", 2f),
        ColumnConfig("Age", 1f),
        ColumnConfig("Size", 2f),
        ColumnConfig("Trained", 1f),
        ColumnConfig("Owner ID", 2f),
        ColumnConfig("Edit", 2f),
        ColumnConfig("Delete", 2f)
    )

    CommonList(
        items = dogs,
        titleRow = { CommonTitleRow(columns = dogColumns) },
        itemRow = { dog ->
            val dogData = listOf(
                ColumnData(dog.id.toString(), 1f),
                ColumnData(dog.name, 2f),
                ColumnData(dog.breed, 2f),
                ColumnData(dog.age.toString(), 1f),
                ColumnData(dog.size, 2f),
                ColumnData(if (dog.isTrained) "Yes" else "No", 1f),
                ColumnData(dog.ownerId?.toString() ?: "None", 2f),
                ColumnData(
                    text = "Edit",
                    weight = 2f,
                    isClickable = true,
                    color = Color(0xFF2196F3),
                    onClick = { onEdit(dog) }
                ),
                ColumnData(
                    text = "Delete",
                    weight = 2f,
                    isClickable = true,
                    color = Color(0xFFF44336),
                    onClick = { delete(dog.id) }
                )
            )
            CommonItemRow(columns = dogData)
        }
    )
}