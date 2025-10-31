package com.example.mycomposeapplication

sealed class NavRoutes(val route: String) {
    object Owners : NavRoutes("owners")
    object Cats : NavRoutes("cats")
    object Dogs : NavRoutes("dogs")
}