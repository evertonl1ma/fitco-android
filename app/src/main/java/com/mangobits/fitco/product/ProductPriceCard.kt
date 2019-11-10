package com.mangobits.fitco.product

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.mangobits.startupkit.core.address.AddressInfo
import com.mangobits.startupkit.user.InfoUrl
import com.mangobits.startupkit.user.UserCreator
import org.w3c.dom.Comment
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList

@JsonIgnoreProperties(ignoreUnknown = true)
class ProductPriceCard : Serializable {


    var priceCurrent: Double? = null
    var dateCurrent: Date? = null
    var pricePrevious: Double? = null
    var datePrevious: Date? = null
    var desc: String? = null
    var originPrice: String? = null
    var listProductPriceCardDetail: ArrayList<ProductPriceCardDetail>? = null
    var isExpanded: Boolean = false


}