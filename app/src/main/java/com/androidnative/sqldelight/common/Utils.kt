package com.androidnative.sqldelight.common

import android.content.Context
import android.util.DisplayMetrics
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun rememberWindowInfo():WindowInfo{
    val configuration = LocalConfiguration.current
    return WindowInfo(
        screenWidthInfo = when{
            configuration.screenWidthDp<600->WindowInfo.WindowType.Compact
            configuration.screenWidthDp<840->WindowInfo.WindowType.Medium
            else->WindowInfo.WindowType.Expanded
        },
        screenHeightInfo = when{
            configuration.screenHeightDp<400->WindowInfo.WindowType.Compact
            configuration.screenHeightDp<900->WindowInfo.WindowType.Medium
            else->WindowInfo.WindowType.Expanded
        },
        screenWidth = configuration.screenWidthDp.dp,
        ScreenHeight = configuration.screenHeightDp.dp
    )
}


data class WindowInfo(
    val screenWidthInfo:WindowType,
    val screenHeightInfo:WindowType,
    val screenWidth:Dp,
    val ScreenHeight:Dp,
){
    sealed class WindowType{
        object Compact:WindowType()
        object Medium:WindowType()
        object Expanded:WindowType()
    }
}

fun convertDpToPixel(context:Context,dp:Float):Float = dp*(context.resources.displayMetrics.densityDpi/DisplayMetrics.DENSITY_DEFAULT)
fun convertDpToPixel(context:Context,dp:Dp):Float = dp.value*(context.resources.displayMetrics.densityDpi/DisplayMetrics.DENSITY_DEFAULT)
fun convertPxToDp(context:Context,pixel:Float):Float = pixel/(context.resources.displayMetrics.densityDpi/DisplayMetrics.DENSITY_DEFAULT)

val TAG = "DEBUG"