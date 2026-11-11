
package com.narendra.applock

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.widget.*
import java.io.File

class VaultActivity : Activity() {

    private val requestCode = 100
    private lateinit var list: LinearLayout

    private fun vaultFolder(): File {
        return File(filesDir, "private_vault").apply {
            if (!exists()) mkdirs()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(24, 24, 24, 24)
            setBackgroundColor(Color.rgb(10, 18, 38))
        }

        val title = TextView(this).apply {
            text = "NARENDRA PRIVATE VAULT"
            textSize = 22f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
        }

        val add = Button(this).apply {
            text = "Add Photo / Video"
            setOnClickListener {
                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                    type = "*/*"
                    putExtra(Intent.EXTRA_MIME_TYPES,
                        arrayOf("image/*", "video/*"))
                    addCategory(Intent.CATEGORY_OPENABLE)
                }
                startActivityForResult(intent, requestCode)
            }
        }

        list = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
        }

        layout.addView(title)
        layout.addView(add)
        layout.addView(list)

        setContentView(ScrollView(this).apply {
            addView(layout)
        })

        refreshList()
    }

    @Deprecated("Deprecated Android callback")
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == this.requestCode &&
            resultCode == RESULT_OK) {
            data?.data?.let { uri ->
                try {
                    val mime = contentResolver.getType(uri) ?: ""
                    if (!mime.startsWith("image/") &&
                        !mime.startsWith("video/")) {
                        Toast.makeText(this, "Only photos and videos allowed",
                            Toast.LENGTH_SHORT).show()
                        return
                    }

                    val extension = if (mime.startsWith("video/")) ".mp4" else ".jpg"
                    val file = File(vaultFolder(),
                        "vault_${System.currentTimeMillis()}$extension")

                    contentResolver.openInputStream(uri)?.use { input ->
                        file.outputStream().use { output ->
                            input.copyTo(output)
                        }
                    }

                    Toast.makeText(this, "File added to vault",
                        Toast.LENGTH_SHORT).show()
                    refreshList()

                } catch (e: Exception) {
                    Toast.makeText(this, "Import failed",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun refreshList() {
        list.removeAllViews()

        val files = vaultFolder().listFiles() ?: return

        files.forEach { file ->
            val item = TextView(this).apply {
                text = "🔒 ${file.name}"
                textSize = 16f
                setTextColor(Color.WHITE)
                setPadding(12, 16, 12, 16)
            }
            list.addView(item)
        }
    }
}
