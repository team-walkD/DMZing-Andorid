package dmzing.workd.model.review

import java.io.Serializable

data class PostDto(
        var day : Int,
        var postImgUrl : ArrayList<String>? = null,
        var title : String,
        var content : String
) : Serializable