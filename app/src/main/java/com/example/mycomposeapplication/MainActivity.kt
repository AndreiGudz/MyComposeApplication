package com.example.mycomposeapplication

import android.app.Application
import android.content.res.Resources
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mycomposeapplication.practic5.*
import com.example.mycomposeapplication.ui.theme.MyComposeApplicationTheme

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
                    composable(NavRoutes.Cats.route) {
                        owner?.let {
                            val catViewModel: CatViewModel = viewModel(
                                it,
                                "CatViewModel",
                                CatViewModelFactory(LocalContext.current.applicationContext as Application)
                            )
                            ContentWithCats(catViewModel)
                        }
                    }
                    composable(NavRoutes.Dogs.route) {
                        owner?.let {
                            val dogViewModel: DogViewModel = viewModel(
                                it,
                                "DogViewModel",
                                DogViewModelFactory(LocalContext.current.applicationContext as Application)
                            )
                            ContentWithDogs(dogViewModel)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NavBar(navController: NavController) {
    val currentRote = navController.currentDestination?.route
    androidx.compose.material3.BottomAppBar {
        androidx.compose.material3.NavigationBar {
            NavigationBarItem(
                selected = currentRote == NavRoutes.Owners.route,
                onClick = { navController.navigate(NavRoutes.Owners.route) },
                icon = { androidx.compose.material3.Icon(
                    Icons.Default.AccountBox,
                    contentDescription = "Owners",
                    modifier = Modifier.fillMaxSize(0.5f)
                ) },
                label = { Text("Owners") }
            )
            NavigationBarItem(
                selected = currentRote == NavRoutes.Cats.route,
                onClick = { navController.navigate(NavRoutes.Cats.route) },
                icon = { Icon(
                    painterResource(R.drawable.cat),
                    contentDescription = "Cats",
                    modifier = Modifier.fillMaxSize(0.5f)
                ) },
                label = { Text("Cats") }
            )
            NavigationBarItem(
                selected = currentRote == NavRoutes.Dogs.route,
                onClick = { navController.navigate(NavRoutes.Dogs.route) },
                icon = { Icon(
                    painter = painterResource(R.drawable.dog),
                    contentDescription = "Dogs",
                    modifier = Modifier.fillMaxSize(0.5f)
                ) },
                label = { Text("Dogs") }
            )
        }
    }
}