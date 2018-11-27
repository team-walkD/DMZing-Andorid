package dmzing.workd.view.review

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import dmzing.workd.R
import dmzing.workd.model.review.SimpleReviewDto
import dmzing.workd.network.ApplicationController
import dmzing.workd.network.NetworkService
import dmzing.workd.util.SharedPreference
import dmzing.workd.view.adapter.DetailReviewListAdpater
import kotlinx.android.synthetic.main.fragment_detail_review_list.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailReviewListFragment : Fragment() {
    lateinit var detailReviewListAdpater: DetailReviewListAdpater
    lateinit var networkService : NetworkService

    lateinit var jwt : String
    lateinit var mView : View
    var rid : Int = 0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_detail_review_list, container,false)
        var courseId = arguments!!.getInt("courseId")
        networkService = ApplicationController.instance!!.networkService
        jwt = SharedPreference.instance!!.getPrefStringData("jwt","")!!

        getSimpleReviewList(mView,courseId)

        return mView
    }

    override fun onResume() {
        super.onResume()
        var courseId = arguments!!.getInt("courseId")
        getSimpleReviewList(mView,courseId)
    }

    fun getSimpleReviewList(view : View,courseId : Int){
        var type : String? = null
        when(courseId){
            1->{
                type = "DATE"
            }
            2->{
                type = "HISTORY"
            }
            3->{
                type = "ADVENTURE"
            }
        }
        val simpleReviewList = networkService.getSimpleReviews(jwt,rid,type!!)
        simpleReviewList.enqueue(object : Callback<ArrayList<SimpleReviewDto>>{
            override fun onFailure(call: Call<ArrayList<SimpleReviewDto>>, t: Throwable) {
                Log.d("getSimpleReviewList","fail")
            }

            override fun onResponse(
                call: Call<ArrayList<SimpleReviewDto>>,
                response: Response<ArrayList<SimpleReviewDto>>
            ) {
                when(response.code()){
                    200->{
                        Log.d("getSimpleReviewList",response.code().toString())
                        detailReviewListAdpater = DetailReviewListAdpater(response.body()!!,activity!!.applicationContext)
                        detailReviewListAdpater.setOnItemClickListener(object : DetailReviewListAdpater.ItemClick{
                            override fun onClick(view: View, position: Int) {
                                var intent = Intent(context,ReviewActivity::class.java)
                                intent.putExtra("reviewId",response.body()!!.get(position).id)
                                startActivity(intent)
                            }

                        })

                        view.review_list_detail_recycler.layoutManager = LinearLayoutManager(activity)

                        view.review_list_detail_recycler.adapter = detailReviewListAdpater

                    }
                    401->{
                        Log.d("getSimpleReviewList",response.code().toString())
                    }
                    500->{
                        Log.d("getSimpleReviewList",response.code().toString())
                    }
                }
            }

        })
    }
}