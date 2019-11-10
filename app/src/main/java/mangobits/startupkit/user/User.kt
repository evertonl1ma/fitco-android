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
class User : Serializable {


    var id: String? = null

    var idFacebook: String? = null


    var idGoogle: String? = null


    var phone: String? = null


    var phoneNumber: Long? = null


    var phoneCountryCode: Int? = null


    var name: String? = null


    var email: String? = null


    var cpf: String? = null


    var keyIOS: String? = null


    var keyAndroid: String? = null


    var password: String? = null


    var oldPassword: String? = null


    var birthDate: Date? = null


    var birthDateStr: String? = null


    var gender: String? = null

    var language: String? = null

    var token: String? = null

    var type: String? = null

    var idCategory: String? = null
    var nameCategory: String? = null

    var rating: Double? = null

    var listIdsUserFavorite: java.util.ArrayList<String>? = null

    //customer
    //supllier


    //   var info: Map<String, String>? = null
    var info: MutableMap<String, String>? = null
    // idCompany
    // pin
    // cpf
    // rg

}