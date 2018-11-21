package dmzing.workd.model.mypage

/**
 * Created by VictoryWoo
 */
data class GetMyDpPoint (
    var totalDp : Int,
    var dpHistoryDtos : ArrayList<DpHistoryData>
)

data class DpHistoryData(
    var id : Int,
    var createdAt : Int,
    var dpType : String,
    var dp : Int
)

