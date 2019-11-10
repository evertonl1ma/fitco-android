package com.mangobits.fitco.reports

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.mangobits.startupkit.core.address.AddressInfo
import com.mangobits.startupkit.user.InfoUrl
import com.mangobits.startupkit.user.UserCreator
import org.w3c.dom.Comment
import java.io.Serializable
import java.util.*

@JsonIgnoreProperties(ignoreUnknown = true)
class Report : Serializable {


    var id: String? = null
    var idtitlePt: String? = null
    var titleEn: String? = null
    var descriptionPt: String? = null
    var descriptionEn: String? = null
    var status: String? = null
    var creationDate: Date? = null


}