package com.team42.composecollapsingbar

import android.app.Activity
import android.graphics.Bitmap
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asImageBitmap
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

/**
 * A highly customizable **collapsing top app bar** that smoothly animates its layout,
 * avatar size, and content visibility based on scroll behavior.
 *
 * Designed to create rich, profile-style headers (e.g., user profile, author page, etc.),
 * this component animates between an expanded and collapsed state as the user scrolls.
 *
 * It supports:
 * - Animated avatar resizing
 * - Dynamic title and subtitle transitions
 * - Optional bio text
 * - Customizable colors, shapes, and actions
 *
 * **Usage:**
 * ```
 * val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
 *
 * Scaffold(
 *     modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
 *     topBar = {
 *         CollapsingAppBar(
 *             scrollBehavior = scrollBehavior,
 *             title = "Anil Kumar",
 *             subtitle = "Senior Android Developer",
 *             bioText = "Building apps that make life easier.",
 *             profileImage = painterResource(R.drawable.profile),
 *         )
 *     }
 * ) {
 *     LazyColumn(contentPadding = it) {
 *         items(50) { index -> Text("Item #$index") }
 *     }
 * }
 * ```
 *
 * @param scrollBehavior The [TopAppBarScrollBehavior] that controls the collapsing and expanding
 * animation. Connect it to a scrollable container such as `LazyColumn` or `LazyList`.
 *
 * @param title The main text or name displayed in the app bar. It scales and moves as the bar collapses.
 *
 * @param subtitle The text displayed below the title when expanded. It fades out when collapsed.
 *
 * @param bioText An optional bio or description shown only in the expanded state.
 *
 * @param profileImage The image source for the avatar. Accepts:
 * - A [Painter] (e.g., from `painterResource`)
 * - A network image (from an image loading library)
 * - Or `null` to hide the image
 *
 * @param imageMinSize The minimum size (in dp) of the avatar when collapsed. Defaults to `32.dp`.
 *
 * @param imageMaxSize The maximum size (in dp) of the avatar when expanded. Defaults to `70.dp`.
 *
 * @param shape The shape of the avatar. Defaults to [CircleShape], but can be customized
 * (e.g., `RoundedCornerShape(8.dp)` for a square look).
 *
 * @param contentScale Defines how the image should scale within its bounds.
 * Common values are [ContentScale.Crop] or [ContentScale.Fit].
 *
 * @param tintColor An optional color tint applied to the avatar image. Pass `null` to disable tinting.
 *
 * @param borderColor The color of the avatar’s border. Defaults to `MaterialTheme.colorScheme.outlineVariant`.
 *
 * @param borderWidth The width of the avatar’s border. Defaults to `2.dp`.
 *
 * @param showBackButton Whether to display the back navigation icon. Defaults to `true`.
 *
 * @param onBackClicked Callback invoked when the back button is pressed. Ignored if [showBackButton] is `false`.
 *
 * @param actions A composable slot for action items (e.g., icons, menus) displayed on the right side of the top bar.
 *
 * @param expandedBackgroundColor The background color of the app bar when fully expanded.
 *
 * @param collapsedBackgroundColor The background color of the app bar when fully collapsed.
 *
 * @param statusBarColor The color of the status bar.
 *
 * @see TopAppBarDefaults.exitUntilCollapsedScrollBehavior
 * @see androidx.compose.material3.Scaffold
 * @see TopAppBarScrollBehavior
 */
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
)
 {
     val context = LocalContext.current
     val view = LocalView.current
     val window = (context as Activity).window

    val profileImagePainter: Painter = when (profileImage) {
        is Painter -> profileImage
        is Int -> painterResource(id = profileImage)
        is ImageBitmap -> BitmapPainter(profileImage)
        is Bitmap -> BitmapPainter(profileImage.asImageBitmap())
        is String -> rememberAsyncImagePainter(model = profileImage)
        is ImageVector -> rememberVectorPainter(profileImage)
        else -> painterResource(id = R.drawable.dummy_avatar) // default fallback
    }

    // Calculate the collapsing progress (0.0f = collapsed, 1.0f = expanded)
    val progress = 1f - scrollBehavior.state.collapsedFraction

    // Animate avatar size
    val avatarSize by animateDpAsState(targetValue = lerp(imageMinSize, imageMaxSize, progress))

     SideEffect {
         WindowCompat.setDecorFitsSystemWindows(window, false)
         window.statusBarColor = statusBarColor.toArgb()
         WindowInsetsControllerCompat(window, view).isAppearanceLightStatusBars = false
     }

    // Animate title text style
    val nameStyle =
        if (progress > 0.5f) MaterialTheme.typography.headlineSmall else MaterialTheme.typography.titleLarge

    // Animate background color
    val backgroundColor by animateColorAsState(
        targetValue = lerp(collapsedBackgroundColor, expandedBackgroundColor, progress)
    )

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
                            .clip(CircleShape)
                            .border(borderWidth, borderColor, shape)
                    )
                    Column {
                        Text(
                            text = title,
                            style = nameStyle,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onTertiary
                        )
                        // Show subtitle only when mostly expanded
                        if (progress > 0.9f) {
                            Text(
                                text = subtitle,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onTertiary
                            )
                        }
                    }
                }
            },
            navigationIcon = {
                // Conditionally display the back button
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
            // Pass the actions slot directly to the LargeTopAppBar
            actions = actions,
            scrollBehavior = scrollBehavior,
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = backgroundColor,
                scrolledContainerColor = collapsedBackgroundColor,
                navigationIconContentColor = MaterialTheme.colorScheme.onTertiary,
                titleContentColor = MaterialTheme.colorScheme.onTertiary,
                actionIconContentColor = MaterialTheme.colorScheme.onTertiary
            )
        )
        // Show bio text only when mostly expanded
        if (progress > 0.9f) {
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