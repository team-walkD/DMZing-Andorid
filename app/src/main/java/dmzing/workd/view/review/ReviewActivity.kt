package dmzing.workd.view.review

import android.graphics.Color
import android.graphics.PorterDuff
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dmzing.workd.R
import dmzing.workd.dialog.ReviewReportDialog
import dmzing.workd.model.review.DetailReviewDto
import dmzing.workd.model.review.LikeDto
import dmzing.workd.model.review.ReportDto
import dmzing.workd.model.review.reviewDto
import dmzing.workd.network.ApplicationController
import dmzing.workd.network.NetworkService
import dmzing.workd.util.AppBarStateChangeListener
import dmzing.workd.util.SharedPreference
import dmzing.workd.view.adapter.ReviewPostAdapter
import kotlinx.android.synthetic.main.activity_review.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class ReviewActivity : AppCompatActivity() {
    lateinit var mReviewDto : DetailReviewDto
    lateinit var networkService: NetworkService
    lateinit var mReviewPostAdapter : ReviewPostAdapter
    lateinit var jwt : String
    var rid = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)
        rid = intent.getIntExtra("reviewId",0)
        networkService = ApplicationController.instance!!.networkService


        jwt = SharedPreference.instance!!.getPrefStringData("jwt","")!!

        review_appbarlayout.addOnOffsetChangedListener(object : AppBarStateChangeListener(){
            override fun onStateChanged(appBarLayout: AppBarLayout?, state: State?) {
                when(state){
                    AppBarStateChangeListener.State.EXPANDED->{
                        review_like_button.visibility = View.VISIBLE
                    }
                    else->{
                        review_like_button.visibility = View.GONE
                    }
                }
            }

        })

        review_back_button.setOnClickListener {
            finish()
        }

        review_siren_button.setOnClickListener {
            var dialog = ReviewReportDialog(rid,"DETAIL",this)
            dialog.show()
        }

        review_like_button.setOnClickListener{
            val postReviewLikeResponse = networkService.postReviewLike(jwt,rid)
            postReviewLikeResponse.enqueue(object : Callback<LikeDto>{
                override fun onFailure(call: Call<LikeDto>, t: Throwable) {

                }

                override fun onResponse(call: Call<LikeDto>, response: Response<LikeDto>) {
                    when(response.code()){
                        201->{
                            onResume()
                        }
                        401->{

                        }
                        500->{

                        }
                    }
                }

            })
        }

        getReview(rid)
    }

    override fun onResume() {
        super.onResume()

        getReview(rid)
    }

    fun getReview(rid : Int){
        val getReviewDetailResponse = networkService.getDetailReview(jwt,rid)
        getReviewDetailResponse.enqueue(object : Callback<DetailReviewDto> {
            override fun onFailure(call: Call<DetailReviewDto>, t: Throwable) {

            }

            override fun onResponse(call: Call<DetailReviewDto>, response: Response<DetailReviewDto>) {
                when(response.code()){
                    200->{
                        mReviewDto = response.body()!!
                        //썸네일
                        if(mReviewDto.thumbnailUrl==""){
                            //review_thumbnail.setBackgroundColor(Color.parseColor("#e3e3e3"))
                            //review_thumbnail.setColorFilter(Color.parseColor("#7f000000"), PorterDuff.Mode.SRC_OVER)
                        } else {
                            Glide.with(this@ReviewActivity).load(mReviewDto.thumbnailUrl).apply(RequestOptions().centerCrop()).into(review_thumbnail)
                            review_thumbnail.setColorFilter(Color.parseColor("#7f000000"), PorterDuff.Mode.SRC_OVER)
                        }
                        //타이틀
                        review_title.text = mReviewDto.title
                        //날짜
                        review_start_date.text = timeStampToDate(mReviewDto.startAt)
                        review_end_date.text = timeStampToDate(mReviewDto.endAt)

                        //좋아요
                        if(mReviewDto.like){
                            review_like_heart.setImageDrawable(getDrawable(R.drawable.heart_filled_icon))
                        } else {
                            review_like_heart.setImageDrawable(getDrawable(R.drawable.heart_icon))
                        }
                        review_like_count.text = mReviewDto.likeCount.toString()

                        mReviewPostAdapter = ReviewPostAdapter(mReviewDto.postDto,mReviewDto.startAt,this@ReviewActivity)

                        review_post_recycler.layoutManager = LinearLayoutManager(this@ReviewActivity)
                        review_post_recycler.adapter = mReviewPostAdapter

                    }
                    401->{

                    }
                    500->{

                    }
                }
            }

        })
    }
    fun timeStampToDate(timeStamp : Long) : String{
        var date : Date = Date(timeStamp)
        var dateF : SimpleDateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
        return dateF.format(date)
    }
}
