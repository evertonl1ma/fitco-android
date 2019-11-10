package com.mangobits.fitco.contact

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mangobits.fitco.R
import kotlinx.android.synthetic.main.contact.*
import android.content.Intent
import android.net.Uri


class ContactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.contact)

        title = getString(R.string.contact)

        txtEmail.setOnClickListener(clickEmail)

    }


    var clickEmail = View.OnClickListener {

        val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "contact@fitco.com.br", null))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject")
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Body")
        startActivity(Intent.createChooser(emailIntent, "Send email..."))

    }
}
