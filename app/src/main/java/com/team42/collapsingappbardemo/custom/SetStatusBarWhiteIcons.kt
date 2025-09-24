package com.team42.collapsingappbardemo.custom

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

/**
 * Project: CollapsingAppBarDemo
 * File: SetStatusBarWhiteIcons.kt
 * Created By: ANIL KUMAR on 9/24/2025
 * Copyright © 2025 Team42. All rights reserved.
 **/
@Composable
fun SetStatusBarWhiteIcons(grayBlack: Color) {
    val view = LocalView.current
    val window = (view.context as Activity).window

    SideEffect {
        // Set status bar background color (optional)
        window.statusBarColor = grayBlack.toArgb() // or your expanded/collapsed color

        // Set icons/text to light (white) for dark background
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
    }
}
