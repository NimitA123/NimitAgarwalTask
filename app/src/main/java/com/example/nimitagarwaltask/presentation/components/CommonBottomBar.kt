package com.example.nimitagarwaltask.presentation.components


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CurrencyRupee
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nimitagarwaltask.ui.theme.Blue
import com.example.nimitagarwaltask.ui.theme.DarkGray

@Composable
fun BottomBar(navController: NavController, currentRoute: String?) {
    val items = listOf(
        BottomNavItem.Watchlist,
        BottomNavItem.Orders,
        BottomNavItem.Portfolio,
        BottomNavItem.Funds,
        BottomNavItem.Invest
    )

    val selectedColor = Blue
    val unselectedColor = DarkGray

    NavigationBar(
        containerColor = Color(0xFFe4e4e4),
        modifier = Modifier.fillMaxWidth()
    ) {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(BottomNavItem.Portfolio.route) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label,
                            tint = if (currentRoute == item.route) selectedColor else unselectedColor
                        )
                        Text(
                            text = item.label,
                            fontSize = 11.sp,
                            color = if (currentRoute == item.route) selectedColor else unselectedColor
                        )
                    }
                }
            )
        }
    }
}


sealed class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
) {
    object Watchlist : BottomNavItem("watchlist", Icons.Filled.Menu, "Watchlist")
    object Orders : BottomNavItem("orders", Icons.Filled.History, "Orders")
    object Portfolio : BottomNavItem("portfolio", Icons.Filled.Work, "Portfolio")
    object Funds : BottomNavItem("funds", Icons.Filled.CurrencyRupee, "Funds")
    object Invest : BottomNavItem("invest", Icons.Filled.Savings, "Invest")
}