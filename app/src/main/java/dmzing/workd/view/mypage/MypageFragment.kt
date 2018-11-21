package dmzing.workd.view.mypage

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dmzing.workd.R
import dmzing.workd.model.mypage.GetMypageInfomation
import dmzing.workd.network.ApplicationController
import dmzing.workd.network.NetworkService
import dmzing.workd.util.SharedPreference
import dmzing.workd.util.Utils
import dmzing.workd.view.mypage.point.MypagePointActivity
import dmzing.workd.view.mypage.setting.SettingActivity
import kotlinx.android.synthetic.main.fragment_mypage.*
import kotlinx.android.synthetic.main.fragment_mypage.view.*
import org.jetbrains.anko.support.v4.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by VictoryWoo
 */
class MypageFragment : Fragment(), View.OnClickListener, Utils {
    override fun init() {

    }

    fun init(view : View){
        view.mypageCourseBtn.setOnClickListener(this)
        view.mypageReviewBtn.setOnClickListener(this)
        view.mypagePointBtn.setOnClickListener(this)
        view.mypageSettingBtn.setOnClickListener(this)
        networkService = ApplicationController.instance.networkService
        SharedPreference.instance!!.load(context!!)
    }

    override fun onClick(v: View?) {
        when (v!!) {
            mypageCourseBtn -> startActivity<MypageCourseActivity>()
            mypageReviewBtn -> startActivity<MypageReviewActivity>()
            mypagePointBtn -> startActivity<MypagePointActivity>()
            mypageSettingBtn -> startActivity<SettingActivity>()
        }
    }

    lateinit var networkService : NetworkService
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_mypage, container, false)
        init(view)

        return view
    }

    fun getMypageInfo(view : View){
        var jwt : String = SharedPreference.instance!!.getPrefStringData("jwt")!!
        var getMypageInfoResponse = networkService.getMypageUserInformation(jwt)
        getMypageInfoResponse.enqueue(object : Callback<GetMypageInfomation>{
            override fun onFailure(call: Call<GetMypageInfomation>, t: Throwable) {
                Log.v("1121 woo fail:",t.message)
            }

            override fun onResponse(call: Call<GetMypageInfomation>, response: Response<GetMypageInfomation>) {
                when(response!!.code()){
                    200->{
                        view.mypageNickname.text = response.body()!!.nick
                        view.mypageEmail.text = response.body()!!.email
                        view.mypageCourseCount.text = response.body()!!.courseCount.toString()
                        view.mypageReviewCount.text = response.body()!!.reviewCount.toString()
                        view.mypageDpPoint.text = response.body()!!.dp.toString()
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