package dmzing.workd.model.chat

/**
 * Created by VictoryWoo
 */
data class ChataMTypeData (
    var status : Int,
    var message : String,
    var result : ArrayList<ChatMData>
)

data class ChatMData(
    var id : Int,
    var groups : Int,
    var description :String
)