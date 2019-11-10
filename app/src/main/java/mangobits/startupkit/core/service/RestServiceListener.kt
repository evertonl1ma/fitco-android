package com.mangobits.startupkit.core.service

/**
 * Created by diego on 28/12/17.
 */
interface RestServiceListener<in O> {

    fun processCallback(resultado: JsonContainer, dados: O?)
}