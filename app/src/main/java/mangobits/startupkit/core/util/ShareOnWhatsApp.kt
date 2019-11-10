package br.com.ivoucher.util

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.View
import android.widget.Button
import com.mangobits.fitco.R


class ShareOnWhatsApp(private val context: Context) {


     fun shareTextUrl(text: StringBuilder) {

         val sendIntent = Intent()
         sendIntent.action = Intent.ACTION_SEND
         sendIntent.putExtra(Intent.EXTRA_TEXT, text.toString())
         sendIntent.type = "text/plain"
         sendIntent.setPackage("com.whatsapp");

         if (sendIntent.resolveActivity(context.packageManager) != null) {
             context.startActivity(sendIntent);
         }else {

             val alertDialog = android.support.v7.app.AlertDialog.Builder(context)

             alertDialog.setTitle(context.getText(R.string.alert).toString())

             alertDialog.setMessage(context.getText(R.string.whatsapp_not_installed).toString())

             alertDialog.setPositiveButton(context.getText(R.string.ok), DialogInterface.OnClickListener { dialog, index ->


             })

             alertDialog.create()

             alertDialog.show()
         }
    }

}