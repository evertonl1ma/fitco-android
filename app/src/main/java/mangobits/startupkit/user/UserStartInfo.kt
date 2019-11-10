package com.mangobits.startupkit.user

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.io.Serializable

/**
 * Created by angela on 19/03/18.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class UserStartInfo: Serializable {

    var idUser : String? = null

    var keyIOS : String? = null

    var language : String? = null

    var latitude : Double? = null

    var longitude : Double?  = null

}