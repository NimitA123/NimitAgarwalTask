package com.example.nimitagarwaltask.presentation.navigation

sealed class Screen(val route: String) {
    data object Holdings : Screen("Holdings")


}
