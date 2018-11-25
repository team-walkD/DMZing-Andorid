package dmzing.workd.model.review

data class WritePhotoReviewDto(
        var startAt : Long? = null,
        var placeName : String? = null,
        var tag : String? = null,
        var courseId : Int? = null,
        var imageUrl : String? = null
)