package mangobits.startupkit.user

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.io.Serializable

@JsonIgnoreProperties(ignoreUnknown = true)
class Type : Serializable {


    var id: String? = null


    var desc: String? = null
}