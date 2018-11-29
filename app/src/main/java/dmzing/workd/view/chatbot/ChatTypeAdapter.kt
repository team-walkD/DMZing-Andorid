package dmzing.workd.view.chatbot

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import dmzing.workd.CommonData
import dmzing.workd.R
import dmzing.workd.model.chat.*
import dmzing.workd.network.ApplicationController
import dmzing.workd.network.NetworkService
import kotlinx.android.synthetic.main.item_chat_type_list.view.*
import org.jetbrains.anko.toast
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by VictoryWoo
 */
class ChatTypeAdapter(var items: ArrayList<ChatMData>, var context: Context) :
    RecyclerView.Adapter<ChatTypeAdapter.ChatTypeViewHolder>() {

    lateinit var onItemClick: View.OnClickListener
    lateinit var networkService: NetworkService
    lateinit var mItems : ArrayList<ChatMData>
    lateinit var chatting : ArrayList<ChatMessageData>
    lateinit var chatMessageAdapter: ChatMessageAdapter
    fun onItemClickListener(l: View.OnClickListener) {
        onItemClick = l
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatTypeViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat_type_list, parent, false)
        view.setOnClickListener(onItemClick)
        return ChatTypeViewHolder(view)

    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ChatTypeViewHolder, position: Int) {
        holder.chatText.text = items[position].description
        holder.chatButton.setOnClickListener {
            when (items[position].groups) {
                1 -> getChatMInfo(items[position].groups,items[position].description)
                2 -> getChatMInfo(items[position].groups,items[position].description)
                3 -> getChatMInfo(items[position].groups,items[position].description)

            }
        }
    }

    fun getChatMInfo(id: Int,des : String) {
        chatting = ArrayList()

        networkService = ApplicationController.instance.networkService2
        var chatMResponse = networkService.getChatMInfo(id)
        chatMResponse.enqueue(object : Callback<ChataMTypeData>{
            override fun onFailure(call: Call<ChataMTypeData>, t: Throwable) {

            }

            override fun onResponse(call: Call<ChataMTypeData>, response: Response<ChataMTypeData>) {
                if(response!!.isSuccessful){
                    Log.v("900 woo r",response.message())
                    Log.v("900 woo r",response.body()!!.toString())
                    //mItems = ArrayList()

                   // chatMessageAdapter.getData(des)

                    items = response.body()!!.result
                    CommonData.common_list.add(ChatMessageData(0,des))
                    notifyDataSetChanged()


                }
            }

        })
    }

    inner class ChatTypeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var chatButton: RelativeLayout = itemView.chatTypeLayout
        var chatText: TextView = itemView.chatTypeTextItem
    }

}