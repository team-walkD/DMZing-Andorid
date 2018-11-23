package dmzing.workd.view.home

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.support.v7.widget.SnapHelper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dmzing.workd.CommonData
import dmzing.workd.R
import dmzing.workd.model.home.HomeCourseData
import dmzing.workd.model.home.HomeFilterData
import dmzing.workd.model.home.PickCourse
import dmzing.workd.network.ApplicationController
import dmzing.workd.network.NetworkService
import dmzing.workd.util.SharedPreference
import dmzing.workd.view.adapter.HomeCourseAdapter
import dmzing.workd.view.adapter.HomeFilterAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by VictoryWoo
 */
class HomeFragment : Fragment(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v!!) {
            v -> {
                var index = courseList.getChildAdapterPosition(v!!)
                toast("${index}")
            }
        }
    }

    lateinit var homeCourseAdapter: HomeCourseAdapter
    lateinit var courseItems: PickCourse

    lateinit var homeFilterAdapter: HomeFilterAdapter
    lateinit var filterItems: ArrayList<HomeFilterData>

    lateinit var networkService: NetworkService
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_home, container, false)

        networkService = ApplicationController.instance.networkService
        SharedPreference.instance!!.load(context!!)




        filterItems = ArrayList()


        /*FIXME
        * android recyclerview를 viewpager처럼 아이템 하나씩 스크롤 할 수 있도록 하기 위한 방법으로
        * snapHelper라는 것을 사용하면 되고 코드는 아래의 두줄로 충분하다.
        * */
        var snapHelper: SnapHelper = PagerSnapHelper()
        //var snapHelper2 : SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(view.courseList)
        //snapHelper2.attachToRecyclerView(view.homeFilterRv)

        //settingHomeRecyclerview(view)
        //settingFilterRecyclerview(view)
        getHomeMission(view)

        return view
    }

    fun getHomeMission(view: View) {
        var homeResponse = networkService
            .getHomeMissions(SharedPreference.instance!!.getPrefStringData(CommonData.JWT)!!)
        homeResponse.enqueue(object : Callback<HomeCourseData> {
            override fun onFailure(call: Call<HomeCourseData>, t: Throwable) {
                Log.v("853 woo f:", t.message)
            }

            override fun onResponse(call: Call<HomeCourseData>, response: Response<HomeCourseData>) {
                Log.v("853 woo r:", response.code().toString())
                Log.v("853 woo r:", response.body().toString())
                when (response.code()!!) {
                    200 -> {
                        settingFilterItems(view, response.body()!!.purchaseList)
                        settingHomeItems(view, response.body()!!.pickCourse)
                    }
                }
            }

        })


    }

    fun settingFilterItems(view: View, items: ArrayList<HomeFilterData>) {
        filterItems = items
        view.homeFilterRv.setHasFixedSize(true)
        view.homeFilterRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        homeFilterAdapter = HomeFilterAdapter(filterItems, context!!)
        homeFilterAdapter.setItemClickListener(this)
        homeFilterAdapter.setOnFilterSelectListener(object : HomeFilterAdapter.setFilterSelect{
            override fun onFilterSelect(holder: HomeFilterAdapter.HomeFilterViewHolder, position: Int) {
                if(holder.isCheck){
                    holder.isChecked = false
                }else{
                    // recyclerview의 아이템 들 중에 체크가 되어 있는지 for문을 통해서 검사하는 과
                    for(i in 0 until view.homeFilterRv.childCount){
                        var viewHolder = view.homeFilterRv.findViewHolderForAdapterPosition(i)
                                as HomeFilterAdapter.HomeFilterViewHolder
                        if(viewHolder.isCheck){
                            viewHolder.isChecked = false
                        }
                    }
                    holder.isChecked = true
                    //toast("${holder.fiter_map.text}")
                    toast("${items[position].id}+${holder.fiter_map.text}")

                }
            }

        })
        view.homeFilterRv.adapter = homeFilterAdapter

    }

    fun settingHomeItems(view: View, items : PickCourse){
        courseItems = items
        view.courseList.setHasFixedSize(true)
        view.courseList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        homeCourseAdapter = HomeCourseAdapter(courseItems, context!!)
        homeCourseAdapter.setOnItemClick(this)
        view.courseList.adapter = homeCourseAdapter
    }


    fun settingHomeRecyclerview(view: View) {
        view.courseList.setHasFixedSize(true)
        view.courseList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        homeCourseAdapter = HomeCourseAdapter(courseItems, context!!)
        homeCourseAdapter.setOnItemClick(this)
        view.courseList.adapter = homeCourseAdapter
    }
}