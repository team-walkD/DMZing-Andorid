package dmzing.workd.model.map

import java.io.Serializable

data class CourseDetailDto (
        var backgroundImageUrl : String? = null,
        var estimatedTime : Double,
        var backgroundGifUrl : String,
        var id : Int,
        var imageUrl : String? = null,
        var level : String? = null,
        var lineImageUrl : String? = null,
        var mainDescription : String? = null,
        var places : ArrayList<PlaceDto>? = null,
        var price : Int,
        var pickCount : Int,
        var reviewCount : Int,
        var subDescription : String? = null,
        var title : String? = null

) : Serializable