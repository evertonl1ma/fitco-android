package com.mangobits.fitco.post

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.mangobits.startupkit.core.address.AddressInfo
import com.mangobits.startupkit.user.InfoUrl
import com.mangobits.startupkit.user.UserCreator
import org.w3c.dom.Comment
import java.io.Serializable
import java.util.*

@JsonIgnoreProperties(ignoreUnknown = true)
class PostSearch : Serializable {

//
//    var idUser: String? = null // user logged
//    var idUserCreator: String? = null // user creator of post
//    var idGroup: String? = null
    var lat: Double? = null
    var log: Double? = null
    var queryString: String? = null
    var page: Int = 0


}