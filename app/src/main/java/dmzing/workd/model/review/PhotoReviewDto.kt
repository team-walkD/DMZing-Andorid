package dmzing.workd.model.review

import dmzing.workd.base.BaseModel

data class PhotoReviewDto (
        var id : Int? = null,
        var startAt : Long? = null,
        var placeName : String? = null,
        var tag : String? = null,
        var courseId : Int? = null,
        var imageUrl : String? = null
) : BaseModel()