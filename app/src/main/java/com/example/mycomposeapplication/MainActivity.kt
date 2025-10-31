package com.example.mycomposeapplication

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mycomposeapplication.practic5.*
import com.example.mycomposeapplication.ui.theme.MyComposeApplicationTheme

// сущности из пр #5 - 3. Животное, Кошка, Собака;

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { MyContent() }
    }
}

@Composable
fun MyContent() {
    val owner = LocalViewModelStoreOwner.current

        val navController = rememberNavController()
        MyComposeApplicationTheme {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                bottomBar = { NavBar(navController) }
            ) { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)) {
                    NavHost(navController, startDestination = NavRoutes.Owners.route) {
                        composable(NavRoutes.Owners.route) {
                            owner?.let {
                                val ownerViewModel: OwnerViewModel = viewModel(
                                    it,
                                    "OwnerViewModel",
                                    OwnerViewModelFactory(LocalContext.current.applicationContext as Application)
                                )
                            ContentWithOwners(ownerViewModel)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NavBar(navController: NavController){
    Row(
        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {

        Text(
            "Home",
            modifier = Modifier
                .clickable {
                    navController.navigate(NavRoutes.Owners.route)
                },
            fontSize = 22.sp,
            color = Color(0xFF6650a4)
        )
    }
}

//@Preview(showBackground = true)
@Composable
fun Preview() {
    NavBar(rememberNavController())
}



