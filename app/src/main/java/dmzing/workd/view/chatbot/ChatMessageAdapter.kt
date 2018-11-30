package dmzing.workd.view.chatbot

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dmzing.workd.CommonData
import dmzing.workd.R
import dmzing.workd.model.chat.ChatMessageData
import dmzing.workd.util.CircleImageView
import kotlinx.android.synthetic.main.item_chat_my_message_list.view.*
import kotlinx.android.synthetic.main.item_chat_walk_d_message_list.view.*
import kotlinx.android.synthetic.main.item_chat_web_list.view.*
import org.w3c.dom.Text

/**
 * Created by VictoryWoo
 */
class ChatMessageAdapter(var items: ArrayList<ChatMessageData>, var context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        CommonData.common_list = items
        if (holder is ChatWalDViewHolder) {
            var walkHolder: ChatWalDViewHolder = holder
            walkHolder.chatMessage.text = items[position].message
            walkHolder.chatId.text = "워크디"
            Glide.with(context!!)
                .load(R.drawable.walk_d_profile_img)
                .apply(RequestOptions().centerCrop())
                .into(walkHolder.chatImage)

        } else if (holder is ChatMyViewHolder) {
            var myViewHolder: ChatMyViewHolder = holder
            myViewHolder.myMessage.text = items[position].message
        }else if(holder is ChatLastViewHolder){
            var lastViewHolder : ChatLastViewHolder = holder
            lastViewHolder.lastChatDesOne.text = CommonData.last_list[0].description
            lastViewHolder.lastChatTitleOne.text = CommonData.last_list[0].title
            Glide.with(context).load(CommonData.last_list[0].img_url).into(lastViewHolder.lastChatImageOne)

            lastViewHolder.lastChatDesTwo.text = CommonData.last_list[1].description
            lastViewHolder.lastChatTitleTwo.text = CommonData.last_list[1].title
            Glide.with(context).load(CommonData.last_list[1].img_url).into(lastViewHolder.lastChatImageTwo)

            lastViewHolder.lastChatDesThree.text = CommonData.last_list[2].description
            lastViewHolder.lastChatTitleThree.text = CommonData.last_list[2].title
            Glide.with(context).load(CommonData.last_list[2].img_url).into(lastViewHolder.lastChatImageThree)


            lastViewHolder.lastHashTag.text = CommonData.chat_type
        }
    }

    val WALK_D_TYPE = 0
    val MY_TYPE = 1
    var LAST_TYPE = 2

    interface getting {
        fun getData(str: String)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == WALK_D_TYPE) {
            var walkdView =
                LayoutInflater.from(parent.context).inflate(R.layout.item_chat_walk_d_message_list, parent, false)
            return ChatWalDViewHolder(walkdView)
        } else if(viewType == MY_TYPE){
            var myView =
                LayoutInflater.from(parent.context).inflate(R.layout.item_chat_my_message_list, parent, false)
            return ChatMyViewHolder(myView)
        }else{
            var lastView = LayoutInflater.from(parent.context).inflate(R.layout.item_chat_web_list,parent,false)
            return ChatLastViewHolder(lastView)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (isPositionWalk(position))
            return WALK_D_TYPE
        else if (isPositionLast(position)) {
            return LAST_TYPE
        } else {
            return MY_TYPE
        }
    }

    fun isPositionWalk(position: Int): Boolean {
        return position % 2 == WALK_D_TYPE
    }

    fun isPositionMy(position: Int): Boolean {
        return position % 2 == MY_TYPE
    }

    fun isPositionLast(position: Int): Boolean {
        return position == 5
    }

    override fun getItemCount(): Int = items.size

    inner class ChatMyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var myMessage: TextView = itemView.chatMyText
    }

    inner class ChatWalDViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var chatImage: de.hdodenhof.circleimageview.CircleImageView = itemView.chatWalkImage
        var chatId: TextView = itemView.chatWalkId
        var chatMessage: TextView = itemView.chatWalMessage
    }

    inner class ChatLastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var lastChatImageOne : de.hdodenhof.circleimageview.CircleImageView = itemView.chat_image_one
        var lastChatImageTwo : de.hdodenhof.circleimageview.CircleImageView = itemView.chat_image_two
        var lastChatImageThree : de.hdodenhof.circleimageview.CircleImageView = itemView.chat_image_three

        var lastChatTitleOne : TextView = itemView.chat_title_one
        var lastChatTitleTwo : TextView = itemView.chat_title_two
        var lastChatTitleThree : TextView = itemView.chat_title_three

        var lastChatDesOne : TextView = itemView.chat_description_one
        var lastChatDesTwo : TextView = itemView.chat_description_two
        var lastChatDesThree : TextView = itemView.chat_description_three

        var lastHashTag : TextView = itemView.lastChatHashtagText

    }
}