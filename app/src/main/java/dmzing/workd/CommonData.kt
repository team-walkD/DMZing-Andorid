package dmzing.workd

import dmzing.workd.model.chat.ChatMData
import dmzing.workd.model.chat.ChatMessageData

object CommonData {
    var user_id: String = ""
    var user_pw: String = ""
    var user_name: String = ""
    var user_nickname: String = ""
    val JWT : String = "jwt"
    var coursedId : Int = 0
    var placeId : Int = 0
    var common_list = ArrayList<ChatMessageData>()
    var last_list = ArrayList<ChatMData>()
    var chat_type : String = ""
    var commonLatitude : Double? = 0.0
    var commonLongitude : Double? = 0.0
}