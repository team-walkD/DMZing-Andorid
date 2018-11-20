package dmzing.workd.view.map

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dmzing.workd.R
import dmzing.workd.model.map.CourseMainDto
import dmzing.workd.network.ApplicationController
import dmzing.workd.network.NetworkService
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
    lateinit var networkService : NetworkService
    lateinit var courseListAdapter : CourseListAdapter
    lateinit var fragmentView : View
    val jwt = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdXRob3JpdHkiOiJVU0VSIiwiaXNzIjoiZG16aW5nIiwiZXhwIjoxNTUxMjQ3Mzk3LCJlbWFpbCI6ImV4YW1wbGVAZ21haWwuY29tIn0.fZ7C8_U9_p02cr6koo_kppY2L_0sIZCUJBu0KW4834c"
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentView = inflater.inflate(R.layout.fragment_map, container, false)
        networkService = ApplicationController.instance!!.networkService

        settingCourseList(fragmentView)

        var snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(fragmentView!!.map_recycler)

        return fragmentView
    }

    override fun onResume() {
        super.onResume()
        settingCourseList(fragmentView)
    }

    fun settingCourseList(view : View){
        var courseList : ArrayList<CourseMainDto>
        val getCourseListResponse = networkService.getCourseList(jwt)
        getCourseListResponse.enqueue(object : Callback<ArrayList<CourseMainDto>> {
            override fun onFailure(call: Call<ArrayList<CourseMainDto>>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<ArrayList<CourseMainDto>>,
                response: Response<ArrayList<CourseMainDto>>
            ) {
                when(response.code()){
                    200->{
                        courseList = response.body()!!
                        courseListAdapter = CourseListAdapter(courseList,context!!)
                        courseListAdapter.SetOnLockClickListener(object : CourseListAdapter.ItemClick{
                            override fun OnClick(view: View, position: Int) {
                                //잠김 코스 해제
                            }

                        })
                        view.map_recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
                        view.map_recycler.adapter = courseListAdapter


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