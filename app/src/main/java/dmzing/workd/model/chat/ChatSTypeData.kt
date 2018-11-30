package dmzing.workd.model.chat

/**
 * Created by VictoryWoo
 */
data class ChatSTypeData (
    var status :Int,
    var message : String,
    var result : ArrayList<ChatSData>
)

data class ChatSData(
    var title : String,
    var description : String,
    var img_url : String
)
