package com.example.mycomposeapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mycomposeapplication.practic5.Owner
import com.example.mycomposeapplication.practic5.OwnerViewModel

@Composable
fun ContentWithOwners(vm: OwnerViewModel = viewModel()) {
    val ownerList by vm.ownersList.observeAsState(listOf())

    Column {
        OutlinedTextField(
            vm.ownerName,
            modifier = Modifier.padding(8.dp).fillMaxWidth(),
            label = { Text("Name") },
            onValueChange = { vm.changeName(it) }
        )

        OutlinedTextField(
            vm.ownerPhone,
            modifier = Modifier.padding(8.dp).fillMaxWidth(),
            label = { Text("Phone") },
            onValueChange = { vm.changePhone(it) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )

        OutlinedTextField(
            vm.ownerEmail,
            modifier = Modifier.padding(8.dp).fillMaxWidth(),
            label = { Text("Email") },
            onValueChange = { vm.changeEmail(it) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        Button(
            { vm.addOwner() },
            Modifier.padding(8.dp)
        ) {
            Text("Add new owner", fontSize = 22.sp)
        }

        OwnerList(
            owners = ownerList,
            delete = { vm.deleteOwner(it) }
        )
    }
}

@Composable
fun OwnerList(owners:List<Owner>, delete:(Long)->Unit) {
    LazyColumn(Modifier.fillMaxWidth()) {
        item {
            OwnerTitleRow()
        }
        items(owners) {
                owner -> OwnerRow(owner) { delete(owner.id) }
        }
    }
}

@Composable
fun OwnerRow(owner:Owner, delete:(Long)->Unit) {
    Row(Modifier.fillMaxWidth().padding(5.dp).background(Color.White)) {
        Text(owner.id.toString(), Modifier.weight(0.1f), fontSize = 22.sp)
        Text(owner.name, Modifier.weight(0.2f), fontSize = 22.sp)
        Text(owner.phone, Modifier.weight(0.2f), fontSize = 22.sp)
        Text(owner.email, Modifier.weight(0.2f), fontSize = 22.sp)
        Text("Delete",
            Modifier.weight(0.2f).clickable { delete(owner.id) },
            color = Color(0xFF6650a4), fontSize = 22.sp)
    }
}
@Composable
fun OwnerTitleRow() {
    Row(Modifier.background(Color.LightGray).fillMaxWidth().padding(5.dp)) {
        Text("Id", color = Color.White,
            modifier = Modifier.weight(0.1f), fontSize = 22.sp)
        Text("Name", color = Color.White,
            modifier = Modifier.weight(0.2f), fontSize = 22.sp)
        Text("Phone", color = Color.White,
            modifier = Modifier.weight(0.2f), fontSize = 22.sp)
        Text("Email", color = Color.White,
            modifier = Modifier.weight(0.2f), fontSize = 22.sp)
        Text("Delete",
            modifier = Modifier.weight(0.2f), fontSize = 22.sp)
    }
}