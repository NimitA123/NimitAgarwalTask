package com.example.nimitagarwaltask.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun CommonText12(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onBackground,
    fontWeight: FontWeight = FontWeight.Normal,
    style: TextStyle = MaterialTheme.typography.bodyMedium

) {
    Text(
        text = text,
        color = color,
        fontSize = 12.sp,
        fontWeight = fontWeight,
        style = style,
        modifier = modifier
    )
}


@Composable
fun CommonText14(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onBackground,
    fontWeight: FontWeight = FontWeight.Normal,
    style: TextStyle = MaterialTheme.typography.bodyMedium

) {
    Text(
        text = text,
        color = color,
        fontSize = 14.sp,
        fontWeight = fontWeight,
        style = style,
        modifier = modifier,
        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
    )
}
