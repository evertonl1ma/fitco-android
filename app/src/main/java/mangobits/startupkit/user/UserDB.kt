package com.mangobits.startupkit.user

import java.io.Serializable

/**
 * Created by angela on 26/02/18.
 */
class UserDB : Serializable {

    var id: String? = null

    var password: String? = null
    //
    var idGoogle: String? = null
    //
    var idFacebook: String? = null
    //
    var type: String? = null

    var name: String? = null
}