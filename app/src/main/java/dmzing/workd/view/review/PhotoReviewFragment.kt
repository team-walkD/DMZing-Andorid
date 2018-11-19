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
import dmzing.workd.R
import dmzing.workd.model.review.PhotoReviewDto
import dmzing.workd.network.ApplicationController
import dmzing.workd.network.NetworkService
import dmzing.workd.util.GridItemDecoration
import dmzing.workd.view.adapter.PhotoReviewListAdapter
import kotlinx.android.synthetic.main.fragment_photo_review_list.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PhotoReviewFragment : Fragment() {
    lateinit var mGridLayoutManager: GridLayoutManager
    lateinit var mPhotoReviewItems : ArrayList<PhotoReviewDto>
    lateinit var mPhotoReviewAdpater : PhotoReviewListAdapter
    lateinit var networkService: NetworkService
    val jwt : String = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdXRob3JpdHkiOiJVU0VSIiwiaXNzIjoiZG16aW5nIiwiZXhwIjoxNTUxMjQ3Mzk3LCJlbWFpbCI6ImV4YW1wbGVAZ21haWwuY29tIn0.fZ7C8_U9_p02cr6koo_kppY2L_0sIZCUJBu0KW4834c"
    var pid : Int = 0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_photo_review_list, container, false)
        var courseId = arguments!!.getInt("courseId")

        networkService = ApplicationController.instance!!.networkService

        mPhotoReviewItems = ArrayList()

//        mPhotoReviewItems.add(PhotoReviewDto(1,0,"평화전망대","#뭐지 #뭐긴",1))
//        mPhotoReviewItems.add(PhotoReviewDto(2,0,"평화전망대","#뭐지 #뭐긴",1))
//        mPhotoReviewItems.add(PhotoReviewDto(3,0,"평화전망대","#뭐지 #뭐긴",1))
//        mPhotoReviewItems.add(PhotoReviewDto(4,0,"평화전망대","#뭐지 #뭐긴",1))
//        mPhotoReviewItems.add(PhotoReviewDto(5,0,"평화전망대","#뭐지 #뭐긴",1))
//        mPhotoReviewItems.add(PhotoReviewDto(6,0,"평화전망대","#뭐지 #뭐긴",1))

        loadPhotoReviewList(view,courseId)

        return view
    }

    fun loadPhotoReviewList(view : View,courseId : Int){
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
        val getPhotoReviewList = networkService.getPhotoReviews(jwt,pid,type!!)
        getPhotoReviewList.enqueue(object : Callback<ArrayList<PhotoReviewDto>>{
            override fun onFailure(call: Call<ArrayList<PhotoReviewDto>>, t: Throwable) {
                Log.d("getPhotoReviewList",": Failure")
            }

            override fun onResponse(
                call: Call<ArrayList<PhotoReviewDto>>,
                response: Response<ArrayList<PhotoReviewDto>>
            ) {
                when(response.code()){
                    200->{
                        Log.d("getPhotoReviewList",":"+response.code())
                        mPhotoReviewAdpater = PhotoReviewListAdapter(response.body()!!,activity!!.applicationContext)

                        mPhotoReviewAdpater.SetOnItemClickListener(object : PhotoReviewListAdapter.ItemClick{
                            override fun onClick(view: View, position: Int) {
                                startActivity(Intent(context,PhotoDetailActivity::class.java))
                            }

                        })

                        mGridLayoutManager = GridLayoutManager(activity,2)
                        view.photo_review_recycler.layoutManager = mGridLayoutManager
                        view.photo_review_recycler.adapter = mPhotoReviewAdpater

                        var px = Math.round(convertDpToPixel(10.2f,activity!!))
                        view.photo_review_recycler.addItemDecoration(GridItemDecoration(px))
                    }
                    401->{

                    }
                    500->{

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