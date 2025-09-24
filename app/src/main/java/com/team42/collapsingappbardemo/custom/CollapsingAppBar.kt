package com.team42.collapsingappbardemo.custom

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import com.team42.collapsingappbardemo.R
import com.team42.collapsingappbardemo.ui.theme.GrayBlack

/**
 * Project: CollapsingAppBarDemo
 * File: CollapsingAppBar.kt
 * Created By: ANIL KUMAR on 9/24/2025
 * Copyright © 2025 Team42. All rights reserved.
 **/

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollapsingAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    title: String,
    subtitle: String,
    bioText: String,
    expandedBackgroundColor: Color = MaterialTheme.colorScheme.primary,
    collapsedBackgroundColor: Color = MaterialTheme.colorScheme.primary
) {
    val progress = 1f - scrollBehavior.state.collapsedFraction
    val avatarSize by animateDpAsState(lerp(32.dp, 80.dp, progress))
    val nameStyle =
        if (progress > 0.5f) MaterialTheme.typography.headlineSmall else MaterialTheme.typography.titleLarge

    val backgroundColor by animateColorAsState(
        targetValue = lerp(collapsedBackgroundColor, expandedBackgroundColor, progress)
    )

    Column(modifier = Modifier
        .fillMaxWidth()
        .background(backgroundColor)) {
        LargeTopAppBar(
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.dummy_avatar),
                        contentDescription = "Avatar",
                        modifier = Modifier
                            .size(avatarSize)
                            .clip(CircleShape)
                            .border(2.dp, Color.Transparent, CircleShape)
                    )
                    Column {
                        Text(
                            text = title,
                            style = nameStyle,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onTertiary
                        )
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
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.onTertiary
                    )
                }
            },
            scrollBehavior = scrollBehavior,
            colors = TopAppBarDefaults.largeTopAppBarColors(
                containerColor = backgroundColor,
                scrolledContainerColor = collapsedBackgroundColor,
                navigationIconContentColor = MaterialTheme.colorScheme.onTertiary,
                titleContentColor = MaterialTheme.colorScheme.onTertiary
            )
        )
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


