package com.example.nimitagarwaltask.presentation.screen


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier

import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.nimitagarwaltask.presentation.components.BottomBar
import com.example.nimitagarwaltask.presentation.components.CommonTopBar
import com.example.nimitagarwaltask.presentation.navigation.HoldingsNavGraph

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: ""

    Scaffold(
        topBar = {
            CommonTopBar(
                title = currentRoute.toString(),
                onSortClick = {

                },
                onSearchClick = {

                }
            )
        },
        bottomBar = {
            BottomBar(navController = navController, currentRoute = currentRoute)
        }
    ) { innerPadding ->

        HoldingsNavGraph(
            navHostController = navController,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
        )
    }
}



