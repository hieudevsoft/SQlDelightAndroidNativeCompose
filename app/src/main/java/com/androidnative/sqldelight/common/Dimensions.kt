package com.androidnative.sqldelight.common

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class Spacing internal constructor(
    val default:Dp = 0.dp,
    val smallest:Dp = 2.dp,
    val small:Dp = 4.dp,
    val normal:Dp = 8.dp,
    val medium:Dp = 16.dp,
    val large:Dp = 24.dp,
    val extraLarge:Dp = 48.dp,
    val largest:Dp = 64.dp
)
internal val LocalSpacing = staticCompositionLocalOf { Spacing() }
val MaterialTheme.spacing:Spacing
    @Composable
    @ReadOnlyComposable
    get() = LocalSpacing.current