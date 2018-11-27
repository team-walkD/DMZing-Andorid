package dmzing.workd.model.review

import dmzing.workd.base.BaseModel

data class reviewDto(
        var title : String,
        var thumbnailUrl : String? = null,
        var courseId : Int,
        var startAt : Long,
        var endAt : Long,
        var postDto : ArrayList<PostDto>
)