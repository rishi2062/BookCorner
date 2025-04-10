package org.example.project.books.presentation.book_detail.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.alert_triangle__2_
import org.jetbrains.compose.resources.painterResource

@Composable
fun BookDetailsScreen(
    modifier: Modifier = Modifier,
    imageUrl: String? = null,
    onFavoriteClick: () -> Unit,
    onBackClick: () -> Unit,
    isFavorite: Boolean = false,
    content: @Composable () -> Unit
) {

    var imageResult by remember {
        mutableStateOf<Result<Painter>?>(null)
    }

    val painter = rememberAsyncImagePainter(
        model = imageUrl,
        onSuccess = {
            val size = it.painter.intrinsicSize
            imageResult = if (size.width > 1 && size.height > 1) {
                Result.success(it.painter)
            } else {
                Result.failure(Exception("Invalid image size"))
            }
        }
    )

    Box {
        Column(modifier = modifier.fillMaxSize()) {
            BlurredImage(
                modifier = modifier,
                painter = painter,
                imageResult = imageResult
            )

            Box(
                modifier = Modifier.fillMaxWidth()
                    .weight(0.7f)
            ) {
            }
        }

        IconButton(
            onClick = onBackClick,
            modifier = Modifier.align(Alignment.TopStart)
                .padding(16.dp)
                .statusBarsPadding()
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Go Back"
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.fillMaxHeight(0.15f))
            ElevatedCard(
                modifier = Modifier.height(260.dp)
                    .aspectRatio(2 / 3f),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.elevatedCardColors(
                    contentColor = Color.Transparent
                ),
                elevation = CardDefaults.elevatedCardElevation(
                    defaultElevation = 15.dp
                )
            ) {
                AnimatedContent(
                    targetState = imageResult
                ) { result ->
                    when (result) {
                        null -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }

                        else -> {
                            Box {
                                Image(
                                    painter = if (result.isSuccess) painter else painterResource(Res.drawable.alert_triangle__2_),
                                    contentDescription = "Book Cover",
                                    modifier = Modifier.fillMaxSize()
                                        .background(Color.Transparent),
                                    contentScale = if (result.isSuccess) ContentScale.Crop else ContentScale.Fit
                                )

                                IconButton(
                                    onClick = onFavoriteClick,
                                    modifier = Modifier.align(Alignment.BottomEnd)
                                        .background(
                                            brush = Brush.radialGradient(
                                                colors =
                                                listOf(Color.Yellow, Color.Transparent)
                                            )
                                        )
                                ) {
                                    Icon(
                                        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                        contentDescription = "Favorite",
                                        tint = Color.Red

                                    )
                                }
                            }
                        }
                    }
                }
            }

            content()
        }
    }


}

@Composable
fun ColumnScope.BlurredImage(
    modifier: Modifier = Modifier,
    painter: AsyncImagePainter,
    imageResult: Result<Painter>?
) {
    Box(
        modifier = modifier.fillMaxWidth()
            .weight(0.3f)
            .background(
                MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)
            )

    ) {
        imageResult?.getOrNull()?.let {
            Image(
                painter = painter,
                contentDescription = "Book Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
                    .blur(20.dp)
                    .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
            )
        }
    }
}