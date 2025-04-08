package org.example.project.books.presentation.book_detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp


enum class ChipSize {
    SMALL,
    REGULAR
}

@Composable
fun BookChip(
    modifier: Modifier = Modifier,
    chipSize: ChipSize = ChipSize.REGULAR,
    chipComponent: @Composable RowScope.() -> Unit
) {
    Box(
        modifier = modifier.widthIn(
            when (chipSize) {
                ChipSize.SMALL -> {
                    50.dp
                }

                ChipSize.REGULAR -> {
                    100.dp
                }
            }
        )
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.secondary)
            .padding(vertical = 8.dp , horizontal = 12.dp),
        contentAlignment = Alignment.Center
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            chipComponent()
        }
    }
}
