package io.team2681.compose.collapsingbar.optimized

import android.app.Activity
import android.graphics.Bitmap
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import coil.compose.rememberAsyncImagePainter
import io.team2681.compose.collapsingbar.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollapsingAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    title: String,
    subtitle: String,
    bioText: String,
    profileImage: Any? = null,
    imageMinSize: Dp = 32.dp,
    imageMaxSize: Dp = 70.dp,
    shape: Shape = CircleShape,
    contentScale: ContentScale = ContentScale.Crop,
    tintColor: Color? = null,
    borderColor: Color = MaterialTheme.colorScheme.outlineVariant,
    borderWidth: Dp = 2.dp,
    showBackButton: Boolean = true,
    onBackClicked: () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    statusBarColor: Color = MaterialTheme.colorScheme.primary,
    expandedBackgroundColor: Color = MaterialTheme.colorScheme.primary,
    collapsedBackgroundColor: Color = MaterialTheme.colorScheme.primary
) {
    val context = LocalContext.current
    val view = LocalView.current

    LaunchedEffect(Unit) {
        val window = (context as Activity).window
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = statusBarColor.toArgb()
        WindowInsetsControllerCompat(window, view).isAppearanceLightStatusBars = false
    }

    val profileImagePainter: Painter = when (profileImage) {
        is Painter -> profileImage
        is Int -> painterResource(id = profileImage)
        is ImageBitmap -> BitmapPainter(profileImage)
        is Bitmap -> BitmapPainter(profileImage.asImageBitmap())
        is String -> rememberAsyncImagePainter(model = profileImage)
        is ImageVector -> rememberVectorPainter(profileImage)
        else -> painterResource(id = R.drawable.dummy_avatar) // default fallback
    }

    val progress = 1f - scrollBehavior.state.collapsedFraction
    val avatarSize = lerp(imageMinSize, imageMaxSize, progress)
    val backgroundColor = lerp(collapsedBackgroundColor, expandedBackgroundColor, progress)
    val contentAlpha = ((progress - 0.8f) / 0.2f).coerceIn(0f, 1f)

    val titleStyleCollapsed = MaterialTheme.typography.headlineSmall
    val titleStyleExpanded = MaterialTheme.typography.headlineSmall
    val nameStyle =
        if (progress > 0.5f) MaterialTheme.typography.headlineSmall else MaterialTheme.typography.titleLarge
    val titleFontSize = lerp(titleStyleCollapsed.fontSize, titleStyleExpanded.fontSize, progress)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
    ) {
        LargeTopAppBar(
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    val colorFilter = tintColor?.let {
                        ColorFilter.tint(it, BlendMode.SrcAtop)
                    }
                    Image(
                        painter = profileImagePainter,
                        contentDescription = "Avatar",
                        colorFilter = colorFilter,
                        contentScale = contentScale,
                        modifier = Modifier
                            .size(avatarSize)
                            .clip(shape)
                            .border(borderWidth, borderColor, shape)
                    )
                    Column (
                        verticalArrangement = Arrangement.Center
                    ){
                        Text(
                            text = title,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onTertiary,
                            style = nameStyle,
                            fontSize = titleFontSize

                        )
                        AnimatedVisibility(
                            visible = progress > 0.8f,
                            enter = fadeIn(animationSpec = tween(150)),
                            exit = fadeOut(animationSpec = tween(150))
                        ) {
                            Text(                                text = subtitle,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onTertiary,
                                modifier = Modifier.graphicsLayer { alpha = contentAlpha }
                            )
                        }
                    }
                }
            },
            navigationIcon = {
                if (showBackButton) {
                    IconButton(onClick = onBackClicked) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onTertiary
                        )
                    }
                }
            },
            actions = actions,
            scrollBehavior = scrollBehavior,
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent,
                scrolledContainerColor = Color.Transparent,
            )
        )

        // **THE FIX: Use AnimatedVisibility**
        // This animates the appearance and disappearance of its content.
        // `expandVertically` and `shrinkVertically` handle the height,
        // while `fadeIn` and `fadeOut` handle the alpha.
        AnimatedVisibility(
            visible = progress > 0.8f, // Condition to be visible
            enter = fadeIn(animationSpec = tween(150)) + expandVertically(),
            exit = fadeOut(animationSpec = tween(150)) + shrinkVertically()
        ) {
            Text(
                text = bioText,
                color = MaterialTheme.colorScheme.onTertiary,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Justify,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
    }
}