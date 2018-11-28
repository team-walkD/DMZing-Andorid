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
import dmzing.workd.R
import dmzing.workd.model.review.PhotoReviewDto
import dmzing.workd.network.ApplicationController
import dmzing.workd.network.NetworkService
import dmzing.workd.util.GridItemDecoration
import dmzing.workd.util.SharedPreference
import dmzing.workd.view.adapter.PhotoReviewListAdapter
import kotlinx.android.synthetic.main.fragment_photo_review_list.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.view.Display
import android.support.v4.content.ContextCompat.getSystemService
import android.view.WindowManager





class PhotoReviewFragment : Fragment() {
    lateinit var mGridLayoutManager: GridLayoutManager
    lateinit var mPhotoReviewItems : ArrayList<PhotoReviewDto>
    lateinit var mPhotoReviewAdpater : PhotoReviewListAdapter
    lateinit var networkService: NetworkService
    lateinit var jwt : String
    lateinit var mView : View
    var pid : Int = 0
    var courseId = 0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_photo_review_list, container, false)
        courseId = arguments!!.getInt("courseId")

        networkService = ApplicationController.instance!!.networkService

        jwt = SharedPreference.instance!!.getPrefStringData("jwt","")!!

        mPhotoReviewItems = ArrayList()

        loadPhotoReviewList(mView,courseId)

        return mView
    }

    override fun onResume() {
        super.onResume()
        loadPhotoReviewList(mView,courseId)
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
                        //Log.d("getPhotoReviewList",":"+response.body()!!.get(0).placeName)
                        mPhotoReviewAdpater = PhotoReviewListAdapter(response.body()!!,context!!)

                        mPhotoReviewAdpater.SetOnItemClickListener(object : PhotoReviewListAdapter.ItemClick{
                            override fun onClick(view: View, position: Int) {
                                var intent = Intent(context,PhotoDetailActivity::class.java)
                                intent.putExtra("photo",response.body()!!.get(position).imageUrl)
                                startActivity(intent)
                            }

                        })

                        mGridLayoutManager = GridLayoutManager(activity,2)
                        view.photo_review_recycler.layoutManager = mGridLayoutManager
                        view.photo_review_recycler.adapter = mPhotoReviewAdpater

                        var px = Math.round(convertDpToPixel(6f,activity!!))
                        val wm = context!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                        val display = wm.defaultDisplay
                        val size = Point()
                        display.getSize(size)
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