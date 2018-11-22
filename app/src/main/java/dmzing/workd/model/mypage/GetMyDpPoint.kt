package dmzing.workd.model.mypage

/**
 * Created by VictoryWoo
 */
data class GetMyDpPoint (
    var totalDp : Long,
    var dpHistoryDtos : ArrayList<DpHistoryData>
)

data class DpHistoryData(
    var id : Long,
    var createdAt : Long,
    var dpType : String,
    var dp : Long
)

