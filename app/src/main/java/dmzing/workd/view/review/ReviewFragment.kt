package dmzing.workd.view.review

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import dmzing.workd.R
import dmzing.workd.model.review.ReviewCountDto
import dmzing.workd.network.ApplicationController
import dmzing.workd.network.NetworkService
import dmzing.workd.util.GridItemDecoration
import dmzing.workd.view.adapter.ReviewMapAdapter
import kotlinx.android.synthetic.main.fragment_review.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList

class ReviewFragment : Fragment() {
    lateinit var gridLayoutManager: GridLayoutManager
    lateinit var mapItemAdapter : ReviewMapAdapter

    lateinit var networkService : NetworkService

    val jwt : String = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdXRob3JpdHkiOiJVU0VSIiwiaXNzIjoiZG16aW5nIiwiZXhwIjoxNTUxMjQ3Mzk3LCJlbWFpbCI6ImV4YW1wbGVAZ21haWwuY29tIn0.fZ7C8_U9_p02cr6koo_kppY2L_0sIZCUJBu0KW4834c"
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_review, container,false)
        networkService = ApplicationController.instance!!.networkService

        registerReviewCategory(view)

        view.review_walkd_button.setOnClickListener { v: View->
            //챗봇 버튼
        }
        return view
    }

    fun registerReviewCategory(view : View){


        val getReviewCountResponse = networkService.getReviewCounts(jwt)
        getReviewCountResponse.enqueue(object : Callback<ArrayList<ReviewCountDto>>{
            override fun onFailure(call: Call<ArrayList<ReviewCountDto>>, t: Throwable) {
                Log.d("reviewCount",": Failure")
            }

            override fun onResponse(
                call: Call<ArrayList<ReviewCountDto>>,
                response: Response<ArrayList<ReviewCountDto>>
            )
            {
                when(response.code()){
                    200->{//성공
                        response.body()!!.add(ReviewCountDto("Comming Soon",0,null,null))

                        gridLayoutManager = GridLayoutManager(activity,2)
                        mapItemAdapter = ReviewMapAdapter(response.body()!!, activity!!.applicationContext)
                        mapItemAdapter.setOnItemClickListener(object : ReviewMapAdapter.Itemclick{
                            override fun onClick(view: View, position: Int) {
                                if(position == 3){
                                    Toast.makeText(context,"준비중입니다!",Toast.LENGTH_LONG).show()
                                } else {
                                    var intent = Intent(activity,ReviewListActivity::class.java)
                                    intent.putExtra("courseId",response.body()!!.get(position).courseId)
                                    intent.putExtra("typeName",response.body()!!.get(position).typeName)
                                    startActivity(intent)
                                }
                            }

                        })
                        view.review_recyclerView.layoutManager = gridLayoutManager
                        view.review_recyclerView.adapter = mapItemAdapter

                        var px = Math.round(convertDpToPixel(28f,activity!!))
                        view.review_recyclerView.addItemDecoration(GridItemDecoration(px))
                    }
                    401->{//권한 없음
                        Log.d("reviewCount",": 401")
                    }
                    500->{//서버에러
                        Log.d("reviewCount",": 500")
                    }
                }
            }
        })
    }

    fun convertDpToPixel(dp : Float,context : Context) : Float{
        var resources = context.resources

        var metrics = resources.displayMetrics

        var px = dp * (metrics.densityDpi / 160f)

        return px
    }

}