package com.narendra.applock

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.view.WindowManager
import android.view.Gravity
import android.view.View
import android.widget.*
import android.graphics.Color

class AppMonitorService : AccessibilityService() {
    private var lockView: View? = null
    private var lastPackage = ""
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event?.eventType != AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) return
        val pkg = event.packageName?.toString() ?: return
        if (pkg == packageName) { removeLock(); return }
        val prefs = getSharedPreferences("narendra_lock", Context.MODE_PRIVATE)
        val protected = listOf("com.whatsapp","com.facebook.katana","com.instagram.android","com.google.android.youtube","com.google.android.gm","com.android.chrome")
        if (protected.contains(pkg) && prefs.getBoolean(pkg, false) && pkg != lastPackage) {
            lastPackage = pkg
            showLock(prefs.getString("pin", "8546") ?: "8546")
        }
    }
    private fun showLock(correctPin: String) {
        if (lockView != null || !SettingsCheck.overlayAllowed(this)) return
        val wm = getSystemService(WINDOW_SERVICE) as WindowManager
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL; gravity = Gravity.CENTER; setPadding(30,30,30,30)
            setBackgroundColor(Color.rgb(10,18,38))
        }
        val title = TextView(this).apply { text = "🔒 Narendra AppLock"; textSize = 24f; setTextColor(Color.WHITE); gravity = Gravity.CENTER }
        val field = EditText(this).apply { hint = "Enter PIN"; inputType = 2; setTextColor(Color.WHITE); gravity = Gravity.CENTER }
        val msg = TextView(this).apply { text = ""; setTextColor(Color.WHITE); gravity = Gravity.CENTER }
        val unlock = Button(this).apply {
            text = "UNLOCK"
            setOnClickListener {
                if (field.text.toString() == correctPin) { removeLock(); lastPackage = "" }
                else msg.text = "Incorrect PIN"
            }
        }
        layout.addView(title); layout.addView(field); layout.addView(unlock); layout.addView(msg)
        val type = if (Build.VERSION.SDK_INT >= 26) WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY else WindowManager.LayoutParams.TYPE_PHONE
        val params = WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, type,
            0, PixelFormat.TRANSLUCENT).apply { gravity = Gravity.CENTER }
        try { wm.addView(layout, params); lockView = layout } catch (_: Exception) { lockView = null }
    }
    private fun removeLock() {
        val v = lockView ?: return
        try { (getSystemService(WINDOW_SERVICE) as WindowManager).removeView(v) } catch (_: Exception) {}
        lockView = null
    }
    override fun onInterrupt() { removeLock() }
    override fun onDestroy() { removeLock(); super.onDestroy() }
}
