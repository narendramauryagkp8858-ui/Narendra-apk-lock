package com.narendra.applock
import android.app.Activity
import android.app.AlertDialog
object AlertDialogBuilder { fun show(a: Activity, message: String) { AlertDialog.Builder(a).setTitle("Private Vault").setMessage(message).setPositiveButton("OK", null).show() } }
