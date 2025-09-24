package com.team42.collapsingappbardemo

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.team42.collapsingappbardemo.custom.CollapsingAppBar
import com.team42.collapsingappbardemo.custom.SetStatusBarWhiteIcons
import com.team42.collapsingappbardemo.ui.theme.GrayBlack

/**
 * Project: CollapsingAppBarDemo
 * File: CollapsingAppBarScreen.kt
 * Created By: ANIL KUMAR on 9/24/2025
 * Copyright © 2025 Team42. All rights reserved.
 **/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollapsingAppBarScreen(modifier: Modifier = Modifier) {
    SetStatusBarWhiteIcons(GrayBlack)
    val items = listOf(
        "https://anilkumar2681.github.io/sample-images/test/photos/1.jpg",
        "https://anilkumar2681.github.io/sample-images/test/photos/2.jpg",
        "https://anilkumar2681.github.io/sample-images/test/photos/3.jpg",
        "https://anilkumar2681.github.io/sample-images/test/photos/4.jpg",
        "https://anilkumar2681.github.io/sample-images/test/photos/5.jpg",
        "https://anilkumar2681.github.io/sample-images/test/photos/6.jpg",
        "https://anilkumar2681.github.io/sample-images/test/photos/7.jpg",
        "https://anilkumar2681.github.io/sample-images/test/photos/8.jpg",
        "https://anilkumar2681.github.io/sample-images/test/photos/9.jpg",
        "https://anilkumar2681.github.io/sample-images/test/photos/10.jpg",
        "https://anilkumar2681.github.io/sample-images/test/photos/11.jpg",
        "https://anilkumar2681.github.io/sample-images/test/photos/12.jpg",
        "https://anilkumar2681.github.io/sample-images/test/photos/13.jpg",
        "https://anilkumar2681.github.io/sample-images/test/photos/14.jpg",
        "https://anilkumar2681.github.io/sample-images/test/photos/15.jpg",
        "https://anilkumar2681.github.io/sample-images/test/photos/16.jpg",
        "https://anilkumar2681.github.io/sample-images/test/photos/17.jpg",
        "https://anilkumar2681.github.io/sample-images/test/photos/18.jpg",
        "https://anilkumar2681.github.io/sample-images/test/photos/19.jpg",
        "https://anilkumar2681.github.io/sample-images/test/photos/20.jpg",
        "https://anilkumar2681.github.io/sample-images/test/photos/21.jpg",
        "https://anilkumar2681.github.io/sample-images/test/photos/22.jpg",
        "https://anilkumar2681.github.io/sample-images/test/photos/23.jpg",
        "https://anilkumar2681.github.io/sample-images/test/photos/24.jpg",
        "https://anilkumar2681.github.io/sample-images/test/photos/25.jpg",
        "https://anilkumar2681.github.io/sample-images/test/photos/26.jpg",
    )

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CollapsingAppBar(
                scrollBehavior = scrollBehavior,
                title = "Anil Kumar",
                subtitle = "@android developer",
                bioText = "Passionate about technology, design, and continuous learning. " +
                        "I love creating mobile apps, exploring new tools. When I’m not coding," +
                        "I enjoy reading, music, and discovering new ideas.",
                expandedBackgroundColor = GrayBlack,
                collapsedBackgroundColor = GrayBlack
            )
        }
    ) { innerPadding ->
        val cornerRadius = 0.dp
        Box(
            modifier = Modifier
                .zIndex(1f)
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp)
                    .background(MaterialTheme.colorScheme.onTertiary)
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .drawBehind {
                        val shadowColor = Color.Black.copy(alpha = 0.25f)
                        val paint = Paint().asFrameworkPaint().apply {
                            color = android.graphics.Color.TRANSPARENT
                            setShadowLayer(
                                4.dp.toPx(), // shadow blur
                                0f, // shadow offset x
                                (-1).dp.toPx(), // shadow offset y
                                shadowColor.toArgb() // shadow color
                            )
                        }

                        drawIntoCanvas {
                            it.nativeCanvas.drawRoundRect(
                                0f,
                                0f,
                                size.width,
                                size.height / 2,
                                cornerRadius.toPx(),
                                cornerRadius.toPx(),
                                paint
                            )
                        }
                    }
                    // background with rounded corner on top side
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(
                            topStart = cornerRadius,
                            topEnd = cornerRadius
                        )
                    )
            ) {
                PhotoGridHeader()
                PhotoGrid(
                    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                    items = items,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoGridHeader(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                text = "Photos",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.outlineVariant)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                text = "Likes",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun PhotoGrid(
    modifier: Modifier = Modifier,
    items: List<String>
) {
    Spacer(modifier.height(8.dp))
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(items = items, key = { it }) { imageUrl ->
            var isLoading by remember { mutableStateOf(true) }

            // Infinite transition for shimmer animation
            val transition = rememberInfiniteTransition()
            val shimmerTranslate by transition.animateFloat(
                initialValue = -300f,
                targetValue = 300f,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 1000, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                )
            )

            // Shimmer gradient brush
            val shimmerBrush = Brush.linearGradient(
                colors = listOf(
                    MaterialTheme.colorScheme.surfaceVariant,
                    MaterialTheme.colorScheme.surface.copy(alpha = 0.3f),
                    MaterialTheme.colorScheme.surfaceVariant
                ),
                start = Offset(shimmerTranslate, 0f),
                end = Offset(shimmerTranslate + 300f, 300f)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(MaterialTheme.shapes.medium)
            ) {
                AsyncImage(
                    modifier = Modifier.matchParentSize(),
                    model = imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    onState = { state ->
                        isLoading = state is AsyncImagePainter.State.Loading
                    }
                )
                if (isLoading) {
                    // Placeholder or shimmer while loading
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .background(shimmerBrush)
                    )
                }
            }
        }
    }
}
