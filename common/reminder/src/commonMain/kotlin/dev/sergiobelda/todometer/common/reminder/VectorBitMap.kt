package dev.sergiobelda.todometer.common.reminder

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector

expect fun getIconBitmap(imageVector: ImageVector): ImageBitmap
