package com.mangobits.startupkit.core.service

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

/**
 * Created by diego on 28/12/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class JsonContainer {

    var success: Boolean? = true

    var desc: String? = null
}
