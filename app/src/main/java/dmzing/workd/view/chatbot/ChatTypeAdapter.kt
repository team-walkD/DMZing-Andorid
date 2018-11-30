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
    lateinit var mItems: ArrayList<ChatMData>
    lateinit var chatting: ArrayList<ChatMessageData>
    lateinit var chatMessageAdapter: ChatMessageAdapter
    lateinit var cView: View
    fun onItemClickListener(l: View.OnClickListener) {
        onItemClick = l
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatTypeViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat_type_list, parent, false)
        view.setOnClickListener(onItemClick)
        cView = view
        return ChatTypeViewHolder(view)

    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ChatTypeViewHolder, position: Int) {
        holder.chatText.text = items[position].description
        holder.chatButton.setOnClickListener {
            if (items[position].id == null) {
                context!!.toast("getChatMInfo")
                when (items[position].groups) {
                    1 -> getChatMInfo(items[position].groups, items[position].description)
                    2 -> getChatMInfo(items[position].groups, items[position].description)
                    3 -> getChatMInfo(items[position].groups, items[position].description)

                }
            } else {
                context!!.toast("getChatSInfo")
                when (items[position].id) {
                    4 -> getChatSInfo(items[position].id!!, items[position].description, position)
                    5 -> getChatSInfo(items[position].id!!, items[position].description, position)
                    6 -> getChatSInfo(items[position].id!!, items[position].description, position)
                    7 -> getChatSInfo(items[position].id!!, items[position].description, position)
                    8 -> getChatSInfo(items[position].id!!, items[position].description, position)
                    9 -> getChatSInfo(items[position].id!!, items[position].description, position)
                    10 -> getChatSInfo(items[position].id!!, items[position].description, position)
                    11 -> getChatSInfo(items[position].id!!, items[position].description, position)
                    12 -> getChatSInfo(items[position].id!!, items[position].description, position)

                }
            }

        }
    }

    // 소분류
    fun getChatSInfo(id: Int, des: String, pos: Int) {
        Log.v("1255 woo getS", id.toString())
        networkService = ApplicationController.instance.networkService2
        var chatSResponse = networkService.getChatSInfo(id)
        chatSResponse.enqueue(object : Callback<ChataMTypeData> {
            override fun onFailure(call: Call<ChataMTypeData>, t: Throwable) {
                Log.v("1255 woo f", t.message)
            }

            override fun onResponse(call: Call<ChataMTypeData>, response: Response<ChataMTypeData>) {
                if (response!!.isSuccessful) {
                    Log.v("1255 woo", response.body()!!.result.toString())
                    CommonData.last_list = response.body()!!.result
                    when (id) {
                        4 -> { // 파주
                            Log.v("1255 id", id.toString())
                            CommonData.common_list.add(ChatMessageData(0, "${des} 관련 장소에 대해 알려줘."))
                            CommonData.common_list.add(ChatMessageData(1, "${des} 관련 장소들이야! 필요할 땐 언제든 워크디를 찾아줘 :D"))
                            CommonData.common_list.add(ChatMessageData(2, "$des"))
                            CommonData.chat_type = "#위치별 #파주"
                        }
                        5 -> { // 고성
                            Log.v("1255 id", id.toString())
                            CommonData.common_list.add(ChatMessageData(0, "${des} 관련 장소에 대해 알려줘."))
                            CommonData.common_list.add(ChatMessageData(1, "${des} 관련 장소들이야! 필요할 땐 언제든 워크디를 찾아줘 :D"))
                            CommonData.common_list.add(ChatMessageData(2, "??"))
                            CommonData.chat_type = "#위치별 #고성"
                        }
                        6 -> { // 화천
                            Log.v("1255 id", id.toString())
                            CommonData.common_list.add(ChatMessageData(0, "${des} 관련 장소에 대해 알려줘."))
                            CommonData.common_list.add(ChatMessageData(1, "${des} 관련 장소들이야! 필요할 땐 언제든 워크디를 찾아줘 :D"))
                            CommonData.common_list.add(ChatMessageData(2, "??"))
                            CommonData.chat_type = "#위치별 #화천"
                        }
                        7 -> { // 데이트
                            Log.v("1255 id", id.toString())
                            CommonData.common_list.add(ChatMessageData(0, "${des} 관련 장소에 대해 알려줘."))
                            CommonData.common_list.add(ChatMessageData(1, "${des} 관련 장소들이야! 필요할 땐 언제든 워크디를 찾아줘 :D"))
                            CommonData.common_list.add(ChatMessageData(2, "??"))
                            CommonData.chat_type = "#테마별 #데이트"
                        }
                        8 -> { // 역사
                            Log.v("1255 id", id.toString())
                            CommonData.common_list.add(ChatMessageData(0, "${des} 관련 장소에 대해 알려줘."))
                            CommonData.common_list.add(ChatMessageData(1, "${des} 관련 장소들이야! 필요할 땐 언제든 워크디를 찾아줘 :D"))
                            CommonData.common_list.add(ChatMessageData(2, "??"))
                            CommonData.chat_type = "#테마별 #역사"
                        }
                        9 -> { // 자연
                            Log.v("1255 id", id.toString())
                            CommonData.common_list.add(ChatMessageData(0, "${des} 관련 장소에 대해 알려줘."))
                            CommonData.common_list.add(ChatMessageData(1, "${des} 관련 장소들이야! 필요할 땐 언제든 워크디를 찾아줘 :D"))
                            CommonData.common_list.add(ChatMessageData(2, "??"))
                            CommonData.chat_type = "#테마별 #자연"
                        }
                        10 -> { // 10대
                            Log.v("1255 id", id.toString())
                            CommonData.common_list.add(ChatMessageData(0, "${des} 관련 장소에 대해 알려줘."))
                            CommonData.common_list.add(ChatMessageData(1, "${des} 관련 장소들이야! 필요할 땐 언제든 워크디를 찾아줘 :D"))
                            CommonData.common_list.add(ChatMessageData(2, "??"))
                            CommonData.chat_type = "#나이별 #10대"
                        }
                        11 -> { // 20,30대
                            Log.v("1255 id", id.toString())
                            CommonData.common_list.add(ChatMessageData(0, "${des} 관련 장소에 대해 알려줘."))
                            CommonData.common_list.add(ChatMessageData(1, "${des} 관련 장소들이야! 필요할 땐 언제든 워크디를 찾아줘 :D"))
                            CommonData.common_list.add(ChatMessageData(2, "??"))
                            CommonData.chat_type = "#나이별 #20,30대"
                        }
                        12 -> { // 40대
                            Log.v("1255 id", id.toString())
                            CommonData.common_list.add(ChatMessageData(0, "${des} 관련 장소에 대해 알려줘."))
                            CommonData.common_list.add(ChatMessageData(1, "${des} 관련 장소들이야! 필요할 땐 언제든 워크디를 찾아줘 :D"))
                            CommonData.common_list.add(ChatMessageData(2, "??"))
                            CommonData.chat_type = "#나이별 #40대"
                        }
                    }
                    //CommonData.common_list.add(ChatMessageData(0, des))
                    items.clear()
                    cView.visibility = View.GONE
                    notifyDataSetChanged()
                } else {
                    Log.v("1255 woo el", response.body()!!.message)
                }
            }

        })
    }

    // 중분류
    fun getChatMInfo(id: Int, des: String) {
        chatting = ArrayList()

        networkService = ApplicationController.instance.networkService2
        var chatMResponse = networkService.getChatMInfo(id)
        chatMResponse.enqueue(object : Callback<ChataMTypeData> {
            override fun onFailure(call: Call<ChataMTypeData>, t: Throwable) {

            }

            override fun onResponse(call: Call<ChataMTypeData>, response: Response<ChataMTypeData>) {
                if (response!!.isSuccessful) {
                    Log.v("900 woo r", response.message())
                    Log.v("900 woo r", response.body()!!.toString())
                    //mItems = ArrayList()

                    // chatMessageAdapter.getData(des)

                    items = response.body()!!.result
                    when (des) {
                        "위치별 DMZing" -> {
                            CommonData.common_list.add(ChatMessageData(0, des)) //
                            CommonData.common_list.add(ChatMessageData(1, "${des} 대해 알려줄게! 어떤 정보를 알려줄까?"))
                        }
                        "테마별 DMZing" -> {
                            CommonData.common_list.add(ChatMessageData(0, des))
                            CommonData.common_list.add(ChatMessageData(1, "${des} 대해 알려줄게! 어떤 정보를 알려줄까?"))
                        }
                        "나이별 DMZing" -> {
                            CommonData.common_list.add(ChatMessageData(0, des))
                            CommonData.common_list.add(ChatMessageData(1, "${des} 대해 알려줄게! 어떤 정보를 알려줄까?"))
                        }
                    }

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