package dmzing.workd.view.mypage.point

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import dmzing.workd.R
import dmzing.workd.model.mypage.GetMyDpPoint
import dmzing.workd.model.mypage.MypagePoint
import dmzing.workd.network.ApplicationController
import dmzing.workd.network.NetworkService
import dmzing.workd.util.SharedPreference
import dmzing.workd.util.Utils
import dmzing.workd.view.adapter.MypagePointAdapter
import kotlinx.android.synthetic.main.activity_mypage_point.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MypagePointActivity : AppCompatActivity(), View.OnClickListener, Utils {

    override fun init() {
        pointBackBtn.setOnClickListener(this)
        networkService = ApplicationController.instance.networkService
        SharedPreference.instance!!.load(this)
    }

    override fun onClick(v: View?) {
        when (v!!) {
            pointBackBtn -> finish()
        }
    }


    lateinit var pointItems: ArrayList<GetMyDpPoint>
    lateinit var mypagePointAdapter: MypagePointAdapter
    lateinit var networkService: NetworkService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage_point)
        init()

        getMypageDp()

        pointItems = ArrayList()


        mypagePointAdapter = MypagePointAdapter(pointItems, this)

        pointListRecyclerview.adapter = mypagePointAdapter
        pointListRecyclerview.layoutManager = LinearLayoutManager(this)


    }

    fun getMypageDp(){
        var dpResponse = networkService.getDpPoint(SharedPreference.instance!!.getPrefStringData("jwt")!!)
        dpResponse.enqueue(object : Callback<GetMyDpPoint>{
            override fun onFailure(call: Call<GetMyDpPoint>, t: Throwable) {
                Log.v("1244 woo f :",t.message)
            }

            override fun onResponse(call: Call<GetMyDpPoint>, response: Response<GetMyDpPoint>) {
                Log.v("1244 woo s :",response.code().toString())
                when(response!!.code()){
                    200->{
                        mypageTotalDp.text = response.body()!!.totalDp.toString()

                    }
                    401->{

                    }
                    500->{

                    }

                }
            }

        })
    }
}
