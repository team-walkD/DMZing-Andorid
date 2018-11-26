package dmzing.workd.view.mypage

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import dmzing.workd.R
import dmzing.workd.model.mypage.MypageReviewData
import dmzing.workd.model.mypage.review.GetMypageReviewData
import dmzing.workd.network.ApplicationController
import dmzing.workd.network.NetworkService
import dmzing.workd.util.SharedPreference
import dmzing.workd.view.adapter.MypageReviewAdapter
import kotlinx.android.synthetic.main.activity_mypage_review.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MypageReviewActivity : AppCompatActivity() {

    lateinit var reviewItems: ArrayList<GetMypageReviewData>
    lateinit var mypageReviewAdapter: MypageReviewAdapter
    lateinit var networkService: NetworkService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage_review)
        networkService = ApplicationController.instance.networkService
        SharedPreference.instance!!.load(this)

        reviewItems = ArrayList()
        getMypageReview()




    }

    fun getMypageReview() {
        var reviewResponse = networkService
            .getMypageReviews(SharedPreference.instance!!.getPrefStringData("jwt")!!)

        reviewResponse.enqueue(object : Callback<ArrayList<GetMypageReviewData>>{
            override fun onFailure(call: Call<ArrayList<GetMypageReviewData>>, t: Throwable) {
                Log.v("755 woo f",t.message)
            }

            override fun onResponse(
                call: Call<ArrayList<GetMypageReviewData>>,
                response: Response<ArrayList<GetMypageReviewData>>) {

                Log.v("755 woo r",response.code().toString())
                Log.v("755 woo r",response.body().toString())
                when(response!!.code()){
                    200->{
                        reviewNested.scrollTo(0, 0)
                        reviewList.setHasFixedSize(true)
                        reviewItems = response.body()!!
                        mypageReviewAdapter = MypageReviewAdapter(reviewItems, this@MypageReviewActivity)
                        reviewList.layoutManager = LinearLayoutManager(this@MypageReviewActivity)!!
                        reviewList.adapter = mypageReviewAdapter
                    }
                }


            }

        })
    }
}
