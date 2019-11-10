package br.com.bestag.bestagandroid.util

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.io.Serializable

/**
 * Created by angela on 26/02/18.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
class PhotoUpload: Serializable {

     var id: String? = null

     var idObject: String? = null

     var idSubObject: String? = null

     var photo: String? = null


     var title: String? = null


     var url: String? = null

     var width: Double? = null

     var height: Double? = null

     var rotate: Int? = null


}