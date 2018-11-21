package dmzing.workd.model.map

data class CourseMainDto (
    var id : Int,
    var imageUrl : String,
    var isPurchased : Boolean,
    var lineImageUrl : String,
    var mainDescription : String,
    var pickCount : Int,
    var price : Int,
    var subDescription : String,
    var title : String
)