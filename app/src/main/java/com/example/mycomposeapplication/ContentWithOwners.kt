package com.example.mycomposeapplication

import android.graphics.fonts.FontStyle
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import com.example.mycomposeapplication.practic5.Owner
import com.example.mycomposeapplication.practic5.OwnerViewModel
import com.example.mycomposeapplication.practic5.OwnerWithAnimals
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp

@Composable
fun ContentWithOwners(vm: OwnerViewModel) {
    val ownerList by vm.ownersList.observeAsState(listOf())
    val ownersWithAnimals by vm.ownersWithAnimals.observeAsState(listOf())
    var expandedOwnerId by remember { mutableStateOf<Long?>(null) }
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        vm.loadOwnersWithAnimals()
    }

    Column(Modifier.verticalScroll(scrollState)) {
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
            ownersWithAnimals = ownersWithAnimals,
            expandedOwnerId = expandedOwnerId,
            onExpandedChange = { ownerId ->
                expandedOwnerId = if (expandedOwnerId == ownerId) null else ownerId
            },
            onEdit = { vm.startEditing(it) },
            delete = { vm.deleteOwner(it) }
        )
    }
}

@Composable
fun OwnerList(
    owners: List<Owner>,
    ownersWithAnimals: List<OwnerWithAnimals>,
    expandedOwnerId: Long?,
    onExpandedChange: (Long) -> Unit,
    onEdit: (Owner) -> Unit,
    delete: (Long) -> Unit,
) {
    val ownerColumns = listOf(
        ColumnConfig("Id", 1f, 16.sp),
        ColumnConfig("Name", 3f, 16.sp),
        ColumnConfig("Phone", 3f, 16.sp),
        ColumnConfig("Email", 4f, 16.sp),
        ColumnConfig("Animals", 1.5f, 16.sp),
        ColumnConfig("Edit", 2f, 16.sp),
        ColumnConfig("Delete", 2f, 16.sp)
    )

    CommonList(
        items = owners,
        titleRow = { CommonTitleRow(columns = ownerColumns) },
        itemRow = { owner ->
            val ownerWithAnimals = ownersWithAnimals.find { it.owner.id == owner.id }
            val isExpanded = expandedOwnerId == owner.id
            val ownerData = listOf(
                ColumnData(owner.id.toString(), 1f, 16.sp),
                ColumnData(owner.name, 3f, 16.sp),
                ColumnData(owner.phone, 3f, 16.sp),
                ColumnData(owner.email, 4f, 16.sp),
                ColumnData(
                    text = if (isExpanded) "Hide" else "Show",
                    weight = 2f,
                    fontSize = 16.sp,
                    isClickable = true,
                    color = Color(0xFF4CAF50),
                    onClick = {  onExpandedChange(owner.id) }
                ),
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

            if (isExpanded) {
                OwnerAnimalsCard(ownerWithAnimals)
            }
        }
    )
}

@Composable
fun OwnerAnimalsCard(ownerWithAnimals: OwnerWithAnimals?) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Заголовок с количеством животных
            Text(
                text = "Animals: ${ownerWithAnimals?.cats?.size ?: 0} cats, ${ownerWithAnimals?.dogs?.size ?: 0} dogs",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Список кошек
            if (!ownerWithAnimals?.cats.isNullOrEmpty()) {
                Text(
                    text = "Cats:",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
                ownerWithAnimals.cats.forEach { cat ->
                    Text(
                        text = "• ${cat.name} - ${cat.breed}, ${cat.age} years old" +
                                if (cat.likeCatnip) " (likes catnip)" else "",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }

            // Список собак
            if (!ownerWithAnimals?.dogs.isNullOrEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Dogs:",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
                ownerWithAnimals.dogs.forEach { dog ->
                    Text(
                        text = "• ${dog.name} - ${dog.breed}, ${dog.age} years old, ${dog.size}" +
                                if (dog.isTrained) " (trained)" else "",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }

            // Сообщение если животных нет
            if (ownerWithAnimals?.cats.isNullOrEmpty() && ownerWithAnimals?.dogs.isNullOrEmpty()) {
                Text(
                    text = "No animals registered for this owner",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}