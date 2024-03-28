package com.example.daily.util

import android.content.Context
import androidx.appcompat.app.AlertDialog
object DialogUtils {
    fun showDialog(context: Context, title: String, message: String) {
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setTitle(title)
        alertDialogBuilder.setMessage(message)
        alertDialogBuilder.setPositiveButton("OK") { dialog, which ->
            dialog.dismiss()
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }


}