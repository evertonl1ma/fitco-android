package com.mangobits.startupkit.core.service

import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.mangobits.fitco.app.App
import com.mangobits.startupkit.user.User
import org.json.JSONObject
import java.util.HashMap

/**
 * Created by diego on 28/12/17.
 */
class RestServiceRequest : JsonObjectRequest {

    constructor(method: Int, url: String, jsonRequest: JSONObject?, listener: Response.Listener<JSONObject>, errorListener: Response.ErrorListener) : super(method, url, jsonRequest, listener, errorListener) {}

    constructor(url: String, jsonRequest: JSONObject, listener: Response.Listener<JSONObject>, errorListener: Response.ErrorListener) : super(url, jsonRequest, listener, errorListener) {}

    @Throws(AuthFailureError::class)
    override fun getHeaders(): Map<String, String> {

        val headers = HashMap(super.getHeaders())

        if (App.user != null){
            val user = App.user
            headers.put("Authorization", "Bearer " + user!!.token)
        }

//        if (PurpleBag.getApplicationBag().getAttribute("user") != null) {
//            val user = PurpleBag.getApplicationBag().getAttribute("user") as User
//            headers.put("Authorization", "Bearer " + user.token)
//        }

        return headers
    }
}