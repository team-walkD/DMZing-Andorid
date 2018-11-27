package dmzing.workd.view.map

import android.content.Intent
import android.net.LinkAddress
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dmzing.workd.R
import dmzing.workd.model.map.CourseDetailDto
import dmzing.workd.model.map.CourseMainDto
import dmzing.workd.network.ApplicationController
import dmzing.workd.network.NetworkService
import dmzing.workd.util.SharedPreference
import dmzing.workd.view.adapter.CourseListAdapter
import kotlinx.android.synthetic.main.fragment_map.*
import kotlinx.android.synthetic.main.fragment_map.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by VictoryWoo
 */
class MapFragment : Fragment() {
    lateinit var networkService: NetworkService
    lateinit var courseListAdapter: CourseListAdapter
    lateinit var fragmentView: View
    lateinit var jwt: String
    lateinit var imageList: ArrayList<String>
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentView = inflater.inflate(R.layout.fragment_map, container, false)
        networkService = ApplicationController.instance!!.networkService
        jwt = SharedPreference.instance!!.getPrefStringData("jwt", "")!!

        imageList = ArrayList()


        settingCourseList(fragmentView)

        var snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(fragmentView!!.map_recycler)





        fragmentView.map_recycler.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                var layoutmanager = recyclerView.layoutManager!! as LinearLayoutManager
                var rvPosition = layoutmanager.findFirstCompletelyVisibleItemPosition()
                Log.v("720 woo ",rvPosition.toString())
                Log.v("720 woo dx ",dx.toString())
                Log.v("720 woo dy ",dy.toString())
                when(rvPosition){
                    0->{
                        Glide.with(context!!).load(imageList[0]).apply(RequestOptions().centerCrop()).into(courseLineImage)
                    }
                    1->{
                        Glide.with(context!!).load(imageList[1]).apply(RequestOptions().centerCrop()).into(courseLineImage)
                    }
                    2->{
                        Glide.with(context!!).load(imageList[2]).apply(RequestOptions().centerCrop()).into(courseLineImage)
                    }
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })

        return fragmentView
    }

    override fun onResume() {
        super.onResume()
        settingCourseList(fragmentView)
    }

    fun settingCourseList(view: View) {
        var courseList: ArrayList<CourseMainDto>
        val getCourseListResponse = networkService.getCourseList(jwt)
        getCourseListResponse.enqueue(object : Callback<ArrayList<CourseMainDto>> {
            override fun onFailure(call: Call<ArrayList<CourseMainDto>>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<ArrayList<CourseMainDto>>,
                response: Response<ArrayList<CourseMainDto>>
            ) {
                when (response.code()) {
                    200 -> {
                        courseList = response.body()!!
                        Log.v("1221 WOO image", response.body()!!.size.toString())
                        for (i in 0..response.body()!!.size - 1)
                            imageList.add(response.body()!![i].lineImageUrl)

                        Log.v("1221 WOO image", imageList.toString())
                        courseListAdapter = CourseListAdapter(courseList, context!!)
                        courseListAdapter.SetOnLockClickListener(object : CourseListAdapter.ItemClick {
                            override fun OnClick(view: View, position: Int) {
                                //잠김 코스 해제
                                var courseOrderDialog = CourseOrderDialog(
                                    courseList.get(position).id,
                                    courseList.get(position).title,
                                    context!!
                                )
                                courseOrderDialog.show()
                            }

                        })
                        courseListAdapter.SetOnMoreClickListener(object : CourseListAdapter.ItemClick {
                            override fun OnClick(view: View, position: Int) {
                                //코스 보러 가기
                                getCourseDetail(courseList.get(position).id, jwt)
                            }

                        })
                        var lm = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                        view.map_recycler.layoutManager = lm




                        view.map_recycler.adapter = courseListAdapter
                    }
                    401 -> {

                    }
                    500 -> {

                    }
                }
            }

        })
    }

    fun getCourseDetail(cid: Int, jwt: String) {
        val getCourseDetailResponse = networkService.getCourseDetail(jwt, cid)
        getCourseDetailResponse.enqueue(object : Callback<CourseDetailDto> {
            override fun onFailure(call: Call<CourseDetailDto>, t: Throwable) {

            }

            override fun onResponse(call: Call<CourseDetailDto>, response: Response<CourseDetailDto>) {
                when (response.code()) {
                    200 -> {
                        var intent = Intent(context, CourseMainActivity::class.java)
                        intent.putExtra("courseDetailDto", response.body())
                        startActivity(intent)
                    }
                    401 -> {

                    }
                    500 -> {

                    }
                }
            }

        })
    }
}