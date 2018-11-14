package dmzing.workd.model.review

import dmzing.workd.base.BaseModel

data class reviewDto(
        var title : String,
        var thumbnailUrl : String,
        var courseId : Int,
        var startAt : Int,
        var endAt : Int,
        var postDto : ArrayList<PostDto>
)