package com.mangobits.startupkit.core.address

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.io.Serializable

/**
 * Created by angela on 15/03/18.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class AddressInfo: Serializable {

    var address: String? = null

    var street: String? = null

    var number: String? = null

    var complement: String? = null

    var reference: String? = null

    var district: String? = null

    var city: String? = null

    var state: String?= null

    var zipCode: String?= null

    var country: String?= null

    var idObj: String?= null

    var latitude: Double? = null

    var longitude: Double? = null
}