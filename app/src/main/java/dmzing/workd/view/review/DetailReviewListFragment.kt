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
import dmzing.workd.view.adapter.DetailReviewListAdpater
import kotlinx.android.synthetic.main.fragment_detail_review_list.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailReviewListFragment : Fragment() {
    lateinit var detailReviewListAdpater: DetailReviewListAdpater
    lateinit var networkService : NetworkService

    val jwt : String = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdXRob3JpdHkiOiJVU0VSIiwiaXNzIjoiZG16aW5nIiwiZXhwIjoxNTUxMjQ3Mzk3LCJlbWFpbCI6ImV4YW1wbGVAZ21haWwuY29tIn0.fZ7C8_U9_p02cr6koo_kppY2L_0sIZCUJBu0KW4834c"
    var rid : Int = 0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_detail_review_list, container,false)
        var courseId = arguments!!.getInt("courseId")
        networkService = ApplicationController.instance!!.networkService

        getSimpleReviewList(view,courseId)

        return view
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

                    }
                    500->{

                    }
                }
            }

        })
    }
}