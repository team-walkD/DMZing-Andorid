package dmzing.workd.view.review

import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import dmzing.workd.R
import dmzing.workd.model.review.ReviewCountDto
import dmzing.workd.network.ApplicationController
import dmzing.workd.network.NetworkService
import dmzing.workd.util.GridItemDecoration
import dmzing.workd.util.SharedPreference
import dmzing.workd.view.ChatbotActivity
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

    lateinit var jwt : String
    lateinit var mView : View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater!!.inflate(R.layout.fragment_review, container,false)
        networkService = ApplicationController.instance!!.networkService

        jwt = SharedPreference.instance!!.getPrefStringData("jwt","")!!

        val wm = context!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val size = Point()
        display.getSize(size)
        mView.review_recyclerView.addItemDecoration(GridItemDecoration(size.x/26))

        registerReviewCategory(mView)

        mView.review_walkd_button.setOnClickListener { v: View->
            //챗봇 버튼
            startActivity(Intent(context, ChatbotActivity::class.java))
        }
        return mView
    }

    override fun onResume() {
        super.onResume()
        registerReviewCategory(mView)
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
                        gridLayoutManager = GridLayoutManager(activity,2)
                        mapItemAdapter = ReviewMapAdapter(response.body()!!, activity!!.applicationContext)
                        mapItemAdapter.setOnItemClickListener(object : ReviewMapAdapter.Itemclick{
                            override fun onClick(view: View, position: Int) {
                                var intent = Intent(activity,ReviewListActivity::class.java)
                                intent.putExtra("courseId",response.body()!!.get(position).courseId)
                                intent.putExtra("typeName",response.body()!!.get(position).typeName)
                                startActivity(intent)
                            }

                        })
                        view.review_recyclerView.layoutManager = gridLayoutManager
                        view.review_recyclerView.isNestedScrollingEnabled = false
                        view.review_recyclerView.setHasFixedSize(false)
                        view.review_recyclerView.adapter = mapItemAdapter


//                        var px = Math.round(convertDpToPixel(14f,activity!!))
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