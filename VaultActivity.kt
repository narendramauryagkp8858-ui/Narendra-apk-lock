package com.narendra.applock

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.*
import java.io.File

class VaultActivity : Activity() {
    private val pickCode = 77
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root = LinearLayout(this).apply { orientation = 1; gravity = Gravity.CENTER; setPadding(24,24,24,24) }
        root.addView(TextView(this).apply { text = "Narendra Private Vault\nFiles are stored inside app-private storage."; textSize = 20f; gravity = Gravity.CENTER })
        root.addView(Button(this).apply { text = "Import photo / video"; setOnClickListener {
            val i = Intent(Intent.ACTION_OPEN_DOCUMENT).apply { type = "*/*"; putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/*","video/*")); addCategory(Intent.CATEGORY_OPENABLE) }
            startActivityForResult(i, pickCode)
        }})
        root.addView(Button(this).apply { text = "View saved files"; setOnClickListener { showFiles() } })
        setContentView(root)
    }
    @Deprecated("Deprecated in Android API")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pickCode && resultCode == RESULT_OK) {
            val uri = data?.data ?: return
            try {
                val dir = File(filesDir, "vault").apply { mkdirs() }
                val name = "vault_${System.currentTimeMillis()}"
                contentResolver.openInputStream(uri)?.use { input -> File(dir, name).outputStream().use { output -> input.copyTo(output) } }
                Toast.makeText(this, "Imported into app-private vault", Toast.LENGTH_LONG).show()
            } catch (_: Exception) { Toast.makeText(this, "Could not import file", Toast.LENGTH_SHORT).show() }
        }
    }
    private fun showFiles() {
        val dir = File(filesDir, "vault")
        val files = dir.listFiles()?.map { it.name } ?: emptyList()
        AlertDialogBuilder.show(this, if (files.isEmpty()) "Vault is empty" else files.joinToString("\n"))
    }
}
