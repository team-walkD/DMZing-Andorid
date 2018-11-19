package dmzing.workd.model.review

data class SimpleReviewDto (
    var id : Int,
    var title : String,
    var thumbnailUrl : String,
    var createdAt : Long,
    var courseId : Int,
    var startAt : Long,
    var endAt : Long,
    var like : Boolean,
    var likeCount : Int
)