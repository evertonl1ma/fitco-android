package com.mangobits.startupkit.core.service

import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.os.Handler
import android.widget.Toast



import com.android.volley.*
import com.android.volley.Response.Listener
import com.fasterxml.jackson.databind.ObjectMapper
import com.mangobits.fitco.R.string.loading
import com.mangobits.fitco.R

import org.json.JSONObject
import mangobits.startupkit.core.util.Constants


/**
 * Created by diego on 28/12/17.
 */
open class RestService(protected var context: Context, service: String) : AsyncTask<Any, String, Void>() {


    val urlBase: String

    protected var messageProgress: String = ""

    private val queue: RequestQueue

    private var progress: ProgressDialog? = null

    protected var showProgress: Boolean = false

    protected var preProcess: RestServicePreProcessListener? = null

    private val searchDelay: Handler

    private var params: Array<*>? = null


    private val search = Runnable {
        if (showProgress) {

            try {
                progress!!.setCanceledOnTouchOutside(false)

                progress!!.setMessage(messageProgress)

                progress!!.show()
            } catch (e: Exception) {
                //nada
            }
        }
    }


    private val tryAgain = Runnable { execute() }


    init {

        urlBase = getUrlWS(context!!, service!!)

        queue = VolleySingleton.getInstance(context!!).requestQueue

        messageProgress = context!!.getText(R.string.loading).toString()


        searchDelay = Handler()
    }


    /**
     *
     * params[0] = callback
     * params[1] = servico
     * params[2] = parametro
     * params[3] = metodo
     * params[4] = classe de retorno
     *
     * @param params
     * @return
     */
    override fun doInBackground(vararg params: Any?): Void? {

        this.params = params

        execute()

        return null
    }


    private fun execute() {

        try {

            if (preProcess != null) {
                preProcess!!.process()
                preProcess = null
            }

            val callback = params!![0] as RestServiceListener<in Any>

            var json: JSONObject? = null

            var method = Request.Method.GET

            if (params!![3] != null && params!![3] == ServiceMethodEnum.POST) {

                val mapper = ObjectMapper()
                val jsonStr = mapper.writeValueAsString(params!![2])
                json = JSONObject(jsonStr)

                method = Request.Method.POST
            } else if (params!![3] == ServiceMethodEnum.POST) {

                method = Request.Method.DELETE
            }


            val url = urlBase + params!![1] as String


            val jsonObjRequest = RestServiceRequest(
                    method,
                    url,
                    json,
                    Listener<JSONObject> { response ->
                        try {

                            val resp = response.toString()

                            val mapper = ObjectMapper()
                            val json = mapper.readValue<JsonContainer>(resp, JsonContainer::class.java!!)

                            var data: Any? = null
                            if (params!!.size > 4) {

                                if (params!![4] !== String::class.java && params!![4] !== Boolean::class.java) {

                                    data = mapper.readValue(response.get("data").toString(), params!![4] as Class<*>)

                                } else {

                                    if (params!![4] === String::class.java) {
                                        data = response.get("data").toString()

                                    } else if (params!![4] === Boolean::class.java) {
                                        data = response.get("data")

                                    }
                                }
                            }

                            if (showProgress) {

                                progress!!.dismiss()
                            }

                            searchDelay.removeCallbacks(search)

                            if (callback != null) {
                                callback.processCallback(json, data)
                            }
                        } catch (e: Exception) {

                            e.printStackTrace()

                            if (showProgress) {
                                progress!!.dismiss()
                            }
                        }
                    }, Response.ErrorListener { error ->
                        if (error is NoConnectionError) {
                            Toast.makeText(context, "No internet Access, Check your internet connection.", Toast.LENGTH_SHORT).show()

                            Handler().postDelayed(tryAgain, 2000)
                        }

                        if (showProgress) {

                            progress!!.dismiss()
                        }
                    }
            )

            jsonObjRequest.retryPolicy = VolleyTimeout.recuperarTimeout()

            queue.add(jsonObjRequest)
        } catch (e: Exception) {

            e.printStackTrace()
        }

    }


    override fun onPreExecute() {

        progress = ProgressDialog(context)

        searchDelay.postDelayed(search, 1000)
    }


    override fun onPostExecute(aVoid: Void?) {


    }

    companion object {

        fun getUrlWS(context: Context, servico: String): String {

            val urlSb = StringBuilder()
            urlSb.append(Constants.SERVICE_URL)
            urlSb.append("/rs/")
            urlSb.append(servico)
            urlSb.append("/")

            return urlSb.toString()
        }
    }


    fun mockService(callback: RestServiceListener<in Any>, returnType: Class<*>, json: String){

        val cont = JsonContainer()
        cont.success = true

        val mapper = ObjectMapper()
        val data: Any = mapper.readValue(json, returnType)
        callback.processCallback(cont, data)
    }
}
