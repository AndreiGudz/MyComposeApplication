package com.example.mycomposeapplication

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import com.example.mycomposeapplication.practic5.Owner
import com.example.mycomposeapplication.practic5.OwnerViewModel

@Composable
fun ContentWithOwners(vm: OwnerViewModel) {
    val ownerList by vm.ownersList.observeAsState(listOf())

    Column {
        CommonTextField(
            value = vm.ownerName,
            onValueChange = { vm.changeName(it) },
            label = "Name"
        )

        CommonTextField(
            value = vm.ownerPhone,
            onValueChange = { vm.changePhone(it) },
            label = "Phone",
            keyboardType = KeyboardType.Phone
        )

        CommonTextField(
            value = vm.ownerEmail,
            onValueChange = { vm.changeEmail(it) },
            label = "Email",
            keyboardType = KeyboardType.Email
        )

        CommonActionButtons(
            isEditing = vm.editingOwnerId != null,
            onUpdate = { vm.editingOwnerId?.let { id -> vm.updateOwner(id) } },
            onCancel = { vm.cancelEditing() },
            onAdd = { vm.addOwner() },
            updateText = "Update Owner",
            addText = "Add new owner"
        )

        OwnerList(
            owners = ownerList,
            onEdit = { vm.startEditing(it) },
            delete = { vm.deleteOwner(it) }
        )
    }
}

@Composable
fun OwnerList(owners: List<Owner>, onEdit: (Owner) -> Unit, delete: (Long) -> Unit) {
    val ownerColumns = listOf(
        ColumnConfig("Id", 1f, 16.sp),
        ColumnConfig("Name", 3f, 16.sp),
        ColumnConfig("Phone", 3f, 16.sp),
        ColumnConfig("Email", 4f, 16.sp),
        ColumnConfig("Edit", 2f, 16.sp),
        ColumnConfig("Delete", 2f, 16.sp)
    )

    CommonList(
        items = owners,
        titleRow = { CommonTitleRow(columns = ownerColumns) },
        itemRow = { owner ->
            val ownerData = listOf(
                ColumnData(owner.id.toString(), 1f, 16.sp),
                ColumnData(owner.name, 3f, 16.sp),
                ColumnData(owner.phone, 3f, 16.sp),
                ColumnData(owner.email, 4f, 16.sp),
                ColumnData(
                    text = "Edit",
                    weight = 2f,
                    fontSize = 16.sp,
                    isClickable = true,
                    color = Color(0xFF2196F3),
                    onClick = { onEdit(owner) }
                ),
                ColumnData(
                    text = "Delete",
                    weight = 2f,
                    fontSize = 16.sp,
                    isClickable = true,
                    color = Color(0xFFF44336),
                    onClick = { delete(owner.id) }
                )
            )
            CommonItemRow(columns = ownerData)
        }
    )
}