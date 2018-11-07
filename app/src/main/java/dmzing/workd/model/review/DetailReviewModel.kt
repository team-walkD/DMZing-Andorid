package dmzing.workd.model.review

import dmzing.workd.base.BaseModel

data class DetailReviewModel (
        var id : Int? = null,
        var title : String? = null,
        var thumbnailUrl : String? = null,
        var createdAt : Int? = null,
        var courseId : Int? = null,
        var startAt : Int? = null,
        var endAt : Int? = null,
        var like : Boolean? = null,
        var likeCount : Int? = null
) : BaseModel()


//"id": 1,
//"title": "dmzing 후기",
//"thumbnailUrl": "dmzing.png",
//"createdAt": 15203333,
//"courseId": 1,
//"startAt": 1522222,
//"endAt": 1533333,
//"like": true,
//"likeCount": 10