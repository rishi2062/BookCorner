package org.example.project.books.presentation.book_list.components


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.alert_triangle__2_
import org.example.project.books.domain.Book
import org.jetbrains.compose.resources.painterResource
import kotlin.math.round

@Composable
fun BookListItem(
    modifier: Modifier = Modifier,
    book: Book,
    onCardClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(32.dp),
        color = Color.White,
        modifier = modifier.clickable { onCardClick() }
    ) {
        Row(
            modifier = Modifier.padding(16.dp)
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            BookImage(imageUrl = book.imageUrl)
            BookDetails(book = book)
            Spacer(Modifier.weight(1f))
            Icon(
                Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                modifier = Modifier.size(36.dp)
            )
        }
    }

}

@Composable
fun BookDetails(modifier: Modifier = Modifier, book: Book) {
    Column(
        modifier = modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = book.title ?: "",
            style = MaterialTheme.typography.titleMedium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        book.authors?.firstOrNull()?.let { author ->
            Text(
                text = author.name ?: "",
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        book.averageRating?.let { rating ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {

                Text(
                    text = "${round(rating) * 10 / 10.0}",
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Icon(Icons.Default.Star, contentDescription = null, tint = Color.Yellow)
            }
        }
    }
}

@Composable
fun RowScope.BookImage(modifier: Modifier = Modifier, imageUrl: String?) {
    var imageLoading by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.height(100.dp),
        contentAlignment = Alignment.Center) {
        when (imageLoading) {
            true -> {
                CircularProgressIndicator()
            }
            false -> {
                AsyncImage(
                    modifier = modifier.aspectRatio(0.70f),
                    model = imageUrl,
                    contentDescription = null,
                    onLoading = {
//                        imageLoading = true
                    },
                    onSuccess = {
                        imageLoading = false
                    },
                    onError = {
                        imageLoading = false
                    },
                    contentScale = ContentScale.Crop,
                    error = painterResource(Res.drawable.alert_triangle__2_)
                )
            }
        }
    }
}