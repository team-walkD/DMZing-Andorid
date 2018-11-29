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
            var myViewHolder : ChatMyViewHolder = holder
            myViewHolder.myMessage.text = items[position].message
        }
    }

    val WALK_D_TYPE = 0
    val MY_TYPE = 1
    var LAST_TYPE = 2

    interface getting{
        fun getData(str : String)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == WALK_D_TYPE) {
            var walkdView =
                LayoutInflater.from(parent.context).inflate(R.layout.item_chat_walk_d_message_list, parent, false)
            return ChatWalDViewHolder(walkdView)
        } else {
            var myView =
                LayoutInflater.from(parent.context).inflate(R.layout.item_chat_my_message_list, parent, false)
            return ChatMyViewHolder(myView)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (isPositionWalk(position))
            return WALK_D_TYPE
        else
            return MY_TYPE
    }

    fun isPositionWalk(position: Int): Boolean {
        return position % 2 == WALK_D_TYPE
    }

    fun isPositionMy(position: Int): Boolean {
        return position % 2 == MY_TYPE
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
}