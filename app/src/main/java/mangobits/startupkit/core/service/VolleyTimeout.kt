package com.mangobits.startupkit.core.service

import com.android.volley.DefaultRetryPolicy

/**
 * Created by diego on 28/12/17.
 */
object VolleyTimeout {

    private val IMEOUT_MS = 60000


    fun recuperarTimeout(): DefaultRetryPolicy {

        return DefaultRetryPolicy(
                IMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
    }
}
