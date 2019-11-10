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
class UserCreator : Serializable {

    var name: String? = null
    var email: String? = null
    var id: String? = null

}