package com.mangobits.startupkit.core.service

import android.content.Context
import android.graphics.Bitmap
import android.support.v4.util.LruCache
import com.android.volley.RequestQueue
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.Volley

/**
 * Created by diego on 28/12/17.
 */
class VolleySingleton private constructor(context: Context) {

    //Fila de execucao
    val requestQueue: RequestQueue

    //Image Loader
    val imageLoader: ImageLoader

    init {


        requestQueue = Volley.newRequestQueue(context)

        imageLoader = ImageLoader(this.requestQueue,
                object : ImageLoader.ImageCache {

                    // Usando LRU (Last Recent Used) Cache
                    private val mCache = LruCache<String, Bitmap>(10)

                    override fun putBitmap(
                            url: String, bitmap: Bitmap) {
                        mCache.put(url, bitmap)
                    }

                    override fun getBitmap(url: String): Bitmap {
                        return mCache.get(url)
                    }

                })

    }

    companion object {

        private var instance: VolleySingleton? = null

        fun getInstance(context: Context): VolleySingleton {

            if (instance == null) {

                instance = VolleySingleton(context)

            }

            return instance as VolleySingleton
        }
    }
}
