package dmzing.workd.model.mypage.review

/**
 * Created by VictoryWoo
 */

data class GetMypageReviewData(
    var id : Int,
    var title : String,
    var thumbnailUrl : String,
    var createdAt : Long,
    var courseId :Int,
    var startAt : Long,
    var endAt : Long,
    var like : Boolean,
    var likeCount : Int
)
