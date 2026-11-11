package com.narendra.applock
import android.content.Context
import android.provider.Settings
object SettingsCheck {
    fun overlayAllowed(context: Context): Boolean = android.os.Build.VERSION.SDK_INT < 23 || Settings.canDrawOverlays(context)
}
