package dmzing.workd.model.review

import dmzing.workd.base.BaseModel

data class PhotoReviewModel (
        var imageUrl : String? = null,
        var startAt : Int? = null,
        var placeName : String? = null,
        var tag : String? = null,
        var courseId : Int? = null
) : BaseModel()