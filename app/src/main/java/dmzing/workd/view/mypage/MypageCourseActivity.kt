package dmzing.workd.view.mypage

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import dmzing.workd.R
import dmzing.workd.model.mypage.CourseDatas

import dmzing.workd.model.mypage.MypageCourseData
import dmzing.workd.network.ApplicationController
import dmzing.workd.network.NetworkService
import dmzing.workd.util.SharedPreference
import dmzing.workd.util.Utils
import dmzing.workd.view.adapter.MypageCourseAdapter
import kotlinx.android.synthetic.main.activity_mypage_course.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MypageCourseActivity : AppCompatActivity(), View.OnClickListener, Utils {
    override fun init() {
        mypageCourseBackBtn.setOnClickListener(this)

        networkService = ApplicationController.instance.networkService
        SharedPreference.instance!!.load(this)
    }

    override fun onClick(v: View?) {
        when (v!!) {
            mypageCourseBackBtn -> finish()
        }
    }

    lateinit var myCourseList: ArrayList<MypageCourseData>
    lateinit var myCourseAdapter: MypageCourseAdapter
    lateinit var networkService: NetworkService
    lateinit var mypageCourseList: List<CourseDatas>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage_course)
        init()

        getMypageCourse()

        // list 초기화
        mypageCourseList = listOf()


    }


    fun getMypageCourse() {
        var courseResponse = networkService.getMypageCourse(SharedPreference.instance!!.getPrefStringData("jwt")!!)
        courseResponse.enqueue(object : Callback<List<CourseDatas>> {
            override fun onFailure(call: Call<List<CourseDatas>>, t: Throwable) {
                Log.v("1113 woo f : ", t.message)
            }

            override fun onResponse(call: Call<List<CourseDatas>>, response: Response<List<CourseDatas>>) {
                when (response!!.code()) {
                    200 -> {
                        myCourseRecycler.setHasFixedSize(true)
                        mypageCourseList = response!!.body()!!
                        myCourseAdapter = MypageCourseAdapter(mypageCourseList, this@MypageCourseActivity)
                        myCourseAdapter.setOnClickListener(this@MypageCourseActivity)
                        myCourseRecycler.layoutManager = LinearLayoutManager(this@MypageCourseActivity)
                        myCourseRecycler.adapter = myCourseAdapter
                    }

                }

            }
        })

    }
}
