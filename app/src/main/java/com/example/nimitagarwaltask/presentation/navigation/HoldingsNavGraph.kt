package com.example.nimitagarwaltask.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.nimitagarwaltask.presentation.components.BottomNavItem
import com.example.nimitagarwaltask.presentation.screen.funds_screen.FundsScreen
import com.example.nimitagarwaltask.presentation.screen.holdings_screen.HoldingsScreen
import com.example.nimitagarwaltask.presentation.screen.invest_screen.InvestScreen
import com.example.nimitagarwaltask.presentation.screen.orders_screen.OrdersScreen
import com.example.nimitagarwaltask.presentation.screen.watchlist_screen.WatchlistScreen

@Composable
fun HoldingsNavGraph(navHostController: NavHostController, modifier: Modifier) {
    NavHost(
        navController = navHostController,
        startDestination = BottomNavItem.Portfolio.route,
        modifier = modifier
    ) {
        composable(BottomNavItem.Portfolio.route) {
            HoldingsScreen(navHostController, modifier)
        }
        composable(BottomNavItem.Funds.route) {
            FundsScreen()
        }
        composable(BottomNavItem.Invest.route) {
            InvestScreen()
        }
        composable(BottomNavItem.Watchlist.route) {
            WatchlistScreen()
        }
        composable(BottomNavItem.Orders.route) {
            OrdersScreen()
        }
    }

}