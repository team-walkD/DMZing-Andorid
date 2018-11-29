package dmzing.workd.model.chat

/**
 * Created by VictoryWoo
 */
data class ChatBTypeData (
    var status : Int,
    var message : String,
    var result : ArrayList<ChatData>
)

data class ChatData(
    var groups : Int,
    var description : String
)