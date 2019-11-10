package com.mangobits.fitco.product

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.mangobits.startupkit.core.address.AddressInfo
import com.mangobits.startupkit.user.InfoUrl
import com.mangobits.startupkit.user.UserCreator
import org.w3c.dom.Comment
import java.io.Serializable
import java.util.*

@JsonIgnoreProperties(ignoreUnknown = true)
class Product : Serializable {


    var name: String? = null
    var desc: String? = null
    var origin: String? = null
    var isExpanded: Boolean = false



}