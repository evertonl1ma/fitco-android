package mangobits.startupkit.core.util

import android.app.AlertDialog
import android.content.Context

/**
 * Created by angela on 26/02/18.
 */
object MessageAlertDialog {

    fun createMsgDialog(ctx: Context, titulo: String, message: String): AlertDialog.Builder {

        val alertDialog = AlertDialog.Builder(ctx)

        alertDialog.setTitle(titulo)

        alertDialog.setMessage(message)

        alertDialog.setNeutralButton("OK") { _, _ -> }

        alertDialog.create()

        return alertDialog
    }
}