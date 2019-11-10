package com.mangobits.startupkit.core.notification

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class Notification {

    var id: String? = null


    var idFrom: String? = null


    var nameFrom: String? = null


    var idLink: String? = null


    var message: String? = null


    var title: String? = null


    var fgReaded: Boolean? = null


    var count: Int? = null


    var typeFrom: String? = null


}