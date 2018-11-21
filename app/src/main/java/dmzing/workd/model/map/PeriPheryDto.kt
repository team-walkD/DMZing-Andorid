package dmzing.workd.model.map

import java.io.Serializable

data class PeriPheryDto (
        var contenttypeoid : Int,
        var firstimage : String? = null,
        var title : String? = null
) : Serializable