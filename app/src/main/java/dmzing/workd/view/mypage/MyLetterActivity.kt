package dmzing.workd.view.mypage

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import dmzing.workd.R
import dmzing.workd.model.letter.MypageLetterDto
import dmzing.workd.network.ApplicationController
import dmzing.workd.network.NetworkService
import dmzing.workd.util.SharedPreference
import dmzing.workd.util.Utils
import dmzing.workd.view.adapter.MypageLetterAdapter
import kotlinx.android.synthetic.main.activity_my_letter.*
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyLetterActivity : AppCompatActivity(), View.OnClickListener, Utils {
    override fun onClick(v: View?) {
        when (v!!) {
            myletter_back_button -> finish()

        }
    }

    override fun init() {

        myletter_back_button.setOnClickListener(this)
        networkService = ApplicationController.instance.networkService
        SharedPreference.instance!!.load(this@MyLetterActivity)
        imageList = ArrayList()

        getData()
    }

    fun getData() {
        id = intent.getIntExtra("cid", 0)
        toast("$id")
    }

    lateinit var letterAdapter: MypageLetterAdapter
    lateinit var networkService: NetworkService
    lateinit var imageList: ArrayList<MypageLetterDto>
    var id: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_letter)

        init()

        getLetter()
    }

    fun getLetter() {
        var jwt = SharedPreference.instance!!.getPrefStringData("jwt")!!
        var letterResponse = networkService.getMypageLetter(jwt, id)
        letterResponse.enqueue(object : Callback<ArrayList<MypageLetterDto>> {
            override fun onFailure(call: Call<ArrayList<MypageLetterDto>>, t: Throwable) {
                Log.v("500 fail", t.message)
            }

            override fun onResponse(
                call: Call<ArrayList<MypageLetterDto>>,
                response: Response<ArrayList<MypageLetterDto>>
            ) {
                Log.v("500 success", response.code().toString())
                when (response.code()) {
                    200 -> {
                        imageList = response.body()!!
                        letterAdapter = MypageLetterAdapter(imageList, this@MyLetterActivity)

                        letter_recycler.layoutManager = LinearLayoutManager(this@MyLetterActivity)
                        letter_recycler.adapter = letterAdapter
                    }
                    401 -> {

                    }
                    500 -> {

                    }
                }
            }

        })
    }
}
