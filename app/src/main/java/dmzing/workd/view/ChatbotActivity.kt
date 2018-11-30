package dmzing.workd.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import dmzing.workd.R
import dmzing.workd.model.chat.*
import dmzing.workd.network.ApplicationController
import dmzing.workd.network.NetworkService
import dmzing.workd.view.chatbot.ChatMessageAdapter
import dmzing.workd.view.chatbot.ChatTypeAdapter
import kotlinx.android.synthetic.main.activity_chatbot.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatbotActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v!!) {
            chatbotXBtn -> finish()
        }
    }

    lateinit var networkService: NetworkService
    lateinit var chatItems: ArrayList<ChatMData>
    lateinit var chatAdapter: ChatTypeAdapter
    lateinit var chatting: ArrayList<ChatMessageData>
    lateinit var chatMessageAdapter: ChatMessageAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatbot)
        networkService = ApplicationController.instance.networkService2
        chatbotXBtn.setOnClickListener(this)
        chatItems = ArrayList()
        chatting = ArrayList()

        chatting.add(ChatMessageData(0, "워크디에게 뭐든 물어봐! 뭐가 궁금해?!"))
        chatMessageAdapter = ChatMessageAdapter(chatting, this)
        chatMessageRv.setHasFixedSize(true)
        chatMessageRv.adapter = chatMessageAdapter
        chatMessageRv.layoutManager = LinearLayoutManager(this)


        getChat()
    }

    // 대분류 불러오기.
    fun getChat() {
        var chatTypeResponse = networkService.getChatInfo()
        chatTypeResponse.enqueue(object : Callback<ChataMTypeData> {
            override fun onFailure(call: Call<ChataMTypeData>, t: Throwable) {

            }

            override fun onResponse(call: Call<ChataMTypeData>, response: Response<ChataMTypeData>) {
                if (response!!.isSuccessful) {
                    Log.v("845 woo", response.message())
                    Log.v("845 woo", response.body().toString())
                    chatItems = response!!.body()!!.result
                    chatAdapter = ChatTypeAdapter(chatItems, this@ChatbotActivity)
                    chatAdapter.onItemClickListener(this@ChatbotActivity)
                    chatTypeRv.setHasFixedSize(true)
                    chatTypeRv.adapter = chatAdapter
                    chatTypeRv.layoutManager =
                            LinearLayoutManager(this@ChatbotActivity, LinearLayoutManager.HORIZONTAL, false)

                }
            }

        })
    }
}
