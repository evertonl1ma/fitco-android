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
class ProductPriceGroup : Serializable {


    var idProduct: String? = null
    var nameProduct: String? = null
    var listPriceCard: ArrayList<ProductPriceCard>? = null
    var priceCard: ProductPriceCard? = null
    var isExpanded: Boolean = false



}