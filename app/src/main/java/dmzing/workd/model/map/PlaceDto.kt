package dmzing.workd.model.map

import java.io.Serializable

data class PlaceDto (
        var address : String? = null,
        var description : String? = null,
        var hint : String? = null,
        var id : Int,
        var infoCenter : String? = null,
        var latitude : Double,
        var letterImageUrl : String? = null,
        var longitude : Double,
        var mainImageUrl : String? = null,
        var parking : String? = null,
        var peripheries : ArrayList<PeriPheryDto>? = null,
        var restDate : String? = null,
        var reward : Int,
        var sequence : Int,
        var subImageUrl : String? = null,
        var title : String? = null

) : Serializable