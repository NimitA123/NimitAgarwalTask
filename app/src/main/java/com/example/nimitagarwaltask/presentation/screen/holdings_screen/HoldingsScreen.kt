package com.example.nimitagarwaltask.presentation.screen.holdings_screen

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults

import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import java.util.Locale
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

import com.example.nimitagarwaltask.R
import com.example.nimitagarwaltask.data.local.HoldingsEntity
import com.example.nimitagarwaltask.presentation.components.CommonText12
import com.example.nimitagarwaltask.presentation.components.CommonText14
import com.example.nimitagarwaltask.presentation.components.HoldingsErrorScreen
import com.example.nimitagarwaltask.presentation.components.ShimmerPortfolioItem
import com.example.nimitagarwaltask.ui.theme.Black
import com.example.nimitagarwaltask.ui.theme.DarkGray
import com.example.nimitagarwaltask.ui.theme.Gray
import com.example.nimitagarwaltask.ui.theme.Green
import com.example.nimitagarwaltask.ui.theme.Red
import com.example.nimitagarwaltask.ui.theme.SilverGray
import com.example.nimitagarwaltask.utils.UiState

@Composable
fun HoldingsScreen(

    viewModel: HoldingsViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.fetchHoldings()
    }
    val state by viewModel.uiState.collectAsState()
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
        .padding(2.dp)) {
        when (state) {
            is UiState.Loading -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.Top
                ) {
                    items(15) {
                        ShimmerPortfolioItem()
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }

            is UiState.Success -> {
                val holdings = (state as UiState.Success<List<HoldingsEntity>>).data
                PortfolioScreen(
                    portfolioItems = holdings,
                    modifier = Modifier.fillMaxSize()
                )
            }

            is UiState.Error -> {
                HoldingsErrorScreen(
                    message = (state as UiState.Error).message,

                )
            }
        }
    }


}

@Composable
fun PortfolioScreen(
    portfolioItems: List<HoldingsEntity>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            items(portfolioItems, key = { it.symbol }) { item ->
                PortfolioItemRow(item)
                HorizontalDivider(color = Color.LightGray, thickness = 0.5.dp)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        ProfitLossCard(portfolioItems = portfolioItems)
    }
}

@Composable
fun PortfolioItemRow(item: HoldingsEntity) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            CommonText14(
                text = item.symbol,
                fontWeight = FontWeight.Medium,
                color = Black
            )
            Spacer(modifier = Modifier.height(16.dp))
            CommonText12(
                text = stringResource(R.string.net_qty, item.quantity),
                fontWeight = FontWeight.SemiBold,
                color = DarkGray
            )
        }

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.End
        ) {
            CommonText14(
                text = stringResource(R.string.ltp, item.ltp),
                fontWeight = FontWeight.SemiBold,
                color = DarkGray,
                style = TextStyle(textAlign = TextAlign.End)
            )

            Spacer(modifier = Modifier.height(16.dp))

            val totalPnL = (item.ltp - item.avgPrice) * item.quantity

            val isProfit = totalPnL >= 0

            val sign = if (isProfit) "" else "-"
            val formattedValue = "%.2f".format(kotlin.math.abs(totalPnL))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                CommonText12(
                    text = "P&L: ",
                    color = Color.Gray,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.width(4.dp)) // Horizontal spacing

                CommonText12(
                    text = "$sign₹$formattedValue",
                    color = if (totalPnL >= 0) Green else Red,
                    fontWeight = FontWeight.Medium
                )
            }


        }
    }
}

@Composable
fun ProfitLossCard(portfolioItems: List<HoldingsEntity>) {
    val currentValue = portfolioItems.sumOf { it.ltp * it.quantity }
    val totalInvestment = portfolioItems.sumOf { it.avgPrice * it.quantity }
    val totalPnL = currentValue - totalInvestment
    val todayPnL = portfolioItems.sumOf { (it.close - it.ltp) * it.quantity }

    var expanded by remember { mutableStateOf(false) }
    val rotationDeg by animateFloatAsState(targetValue = if (expanded) 180f else 0f)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded }
            .animateContentSize(),
        colors = CardDefaults.cardColors(
            containerColor = Gray
        ),

        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                CommonText14(
                    text = stringResource(R.string.profit_loss),
                    fontWeight = FontWeight.Medium,
                    color = Black
                )
                Row(verticalAlignment = Alignment.CenterVertically) {


                            CommonText14(
                                text = String.format(
                                    Locale.US,
                                    "%.2f (₹%.2f%%)",
                                    totalPnL,
                                    if (totalInvestment > 0) totalPnL / totalInvestment * 100 else 0.0
                                ),
                                fontWeight = FontWeight.Medium,
                                color = if (totalPnL >= 0) Green else Red
                            )

                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Expand",
                        modifier = Modifier
                            .size(24.dp)
                            .rotate(rotationDeg),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            if (expanded) {
                Spacer(modifier = Modifier.height(12.dp))
                DetailRow("Current value", "₹${"%.2f".format(currentValue)}")
                DetailRow("Total investment", "₹${"%.2f".format(totalInvestment)}")
                DetailRow(
                    "Today's P&L",
                    "₹${"%.2f".format(todayPnL)}",
                    valueColor = if (todayPnL >= 0) Green else Red
                )
                DetailRow(
                    "Total P&L",
                    "₹${"%.2f".format(totalPnL)}",
                    valueColor = if (totalPnL >= 0) Green else Red
                )
            }
        }
    }
}

@Composable
private fun DetailRow(
    label: String,
    value: String,
    valueColor: Color = Black
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CommonText12(
            text = label,
            fontWeight = FontWeight.Bold,
            color = SilverGray
        )
        CommonText12(
            text = value,
            fontWeight = FontWeight.Medium,
            color = valueColor
        )
    }
}