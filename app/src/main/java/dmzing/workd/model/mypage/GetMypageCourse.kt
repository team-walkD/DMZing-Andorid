package dmzing.workd.model.mypage

/**
 * Created by VictoryWoo
 */
data class GetMypageCourse(
    var courseItem : List<CourseDatas>
)
data class CourseDatas(
    var id : Int,
    var title : String,
    var mainDescription : String,
    var isPicked : Boolean
)

