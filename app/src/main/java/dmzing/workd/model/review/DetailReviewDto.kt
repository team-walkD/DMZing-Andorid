package dmzing.workd.model.review

data class DetailReviewDto (
        var id : Int,
        var title : String,
        var thumbnailUrl : String,
        var createdAt : Long,
        var courseId : Int,
        var startAt : Long,
        var endAt : Long,
        var like : Boolean,
        var likeCount : Int,
        var postDto : ArrayList<PostDto>
)