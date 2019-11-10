package com.mangobits.startupkit.user

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import mangobits.startupkit.user.Type
import java.io.Serializable
import kotlin.collections.ArrayList
import com.mangobits.startupkit.core.address.AddressInfo
import java.util.*


/**
 * Created by angela on 26/02/18.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
class InfoUrl : Serializable {

    var url: String? = null
    var urlPhoto: String? = null
    var title: String? = null
    var desc: String? = null

}