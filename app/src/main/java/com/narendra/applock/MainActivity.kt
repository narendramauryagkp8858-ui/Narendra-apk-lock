
package com.narendra.applock

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.widget.*
import android.graphics.Color

class MainActivity : Activity() {
    private val prefs by lazy {
        getSharedPreferences("narendra_lock", MODE_PRIVATE)
    }

    private val apps = listOf(
        "WhatsApp" to "com.whatsapp",
        "Facebook" to "com.facebook.katana",
        "Instagram" to "com.instagram.android",
        "YouTube" to "com.google.android.youtube",
        "Gmail" to "com.google.android.gm",
        "Chrome" to "com.android.chrome"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!prefs.getBoolean("pin_ready", false)) {
            prefs.edit().putString("pin", "8546")
                .putBoolean("pin_ready", true).apply()
        }
        showPinGate()
    }

    private fun base() = LinearLayout(this).apply {
        orientation = LinearLayout.VERTICAL
        gravity = Gravity.CENTER
        setPadding(28, 28, 28, 28)
        setBackgroundColor(Color.rgb(10, 18, 38))
    }

    private fun label(text: String, size: Float = 18f) =
        TextView(this).apply {
            this.text = text
            textSize = size
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
            setPadding(8, 12, 8, 12)
        }

    private fun button(text: String, action: () -> Unit) =
        Button(this).apply {
            this.text = text
            setOnClickListener { action() }
        }

    private fun showPinGate() {
        val box = base()
        box.addView(label("NARENDRA APPLOCK", 25f))
        box.addView(label("Enter your PIN"))

        val pin = EditText(this).apply {
            hint = "4-digit PIN"
            inputType = 2
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
        }
        box.addView(pin)

        box.addView(button("UNLOCK") {
            if (pin.text.toString() == prefs.getString("pin", "8546")) {
                showDashboard()
            } else {
                Toast.makeText(this, "Wrong PIN", Toast.LENGTH_SHORT).show()
            }
        })
        setContentView(box)
    }

    private fun showDashboard() {
        val box = base()
        box.addView(label("NARENDRA APPLOCK", 25f))
        box.addView(label("Premium Protection"))

        box.addView(button("Enable App Monitoring") {
            startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
        })

        box.addView(button("Allow Display Over Other Apps") {
            startActivity(Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName")
            ))
        })

        box.addView(button("Open Usage Access Settings") {
            startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
        })

        box.addView(label("Choose Apps to Lock", 20f))

        apps.forEach { (name, pkg) ->
            val cb = CheckBox(this).apply {
                text = name
                setTextColor(Color.WHITE)
                isChecked = prefs.getBoolean(pkg, false)
                setOnCheckedChangeListener { _, checked ->
                    prefs.edit().putBoolean(pkg, checked).apply()
                }
            }
            box.addView(cb)
        }

        box.addView(button("Change PIN") { changePin() })
        box.addView(button("Open Private Vault") {
            startActivity(Intent(this, VaultActivity::class.java))
        })

        setContentView(ScrollView(this).apply {
            addView(box)
        })
    }

    private fun changePin() {
        val input = EditText(this).apply {
            hint = "New 4-digit PIN"
            inputType = 2
        }

        android.app.AlertDialog.Builder(this)
            .setTitle("Change PIN")
            .setView(input)
            .setPositiveButton("Save") { _, _ ->
                val value = input.text.toString()
                if (value.length == 4 && value.all { it.isDigit() }) {
                    prefs.edit().putString("pin", value).apply()
                    Toast.makeText(this, "PIN changed", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Enter exactly 4 digits", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
