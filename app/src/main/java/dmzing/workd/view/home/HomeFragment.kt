package dmzing.workd.view.home

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.support.v7.widget.SnapHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dmzing.workd.R
import dmzing.workd.model.home.HomeCourseData
import dmzing.workd.model.home.HomeFilterData
import dmzing.workd.view.adapter.HomeCourseAdapter
import dmzing.workd.view.adapter.HomeFilterAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import org.jetbrains.anko.support.v4.toast

/**
 * Created by VictoryWoo
 */
class HomeFragment : Fragment(), View.OnClickListener {
    override fun onClick(v: View?) {
        when(v!!){
            v->{
                var index = courseList.getChildAdapterPosition(v!!)
                toast("${index}")
            }
        }
    }

    lateinit var homeCourseAdapter: HomeCourseAdapter
    lateinit var courseItems: ArrayList<HomeCourseData>

    lateinit var homeFilterAdapter : HomeFilterAdapter
    lateinit var filterItems : ArrayList<HomeFilterData>
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_home, container, false)



        courseItems = ArrayList()
        filterItems = ArrayList()
        for (i in 0..5) {
            courseItems.add(HomeCourseData(i,R.drawable.demo_image, "중", "3", "50"))
            filterItems.add(HomeFilterData("데이트 맵"))
        }



        /*FIXME
        * android recyclerview를 viewpager처럼 아이템 하나씩 스크롤 할 수 있도록 하기 위한 방법으로
        * snapHelper라는 것을 사용하면 되고 코드는 아래의 두줄로 충분하다.
        * */
        var snapHelper: SnapHelper = PagerSnapHelper()
        var snapHelper2 : SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(view.courseList)
        snapHelper2.attachToRecyclerView(view.homeFilterRv)

        settingHomeRecyclerview(view)
        settingFilterRecyclerview(view)



        return view
    }

    fun settingFilterRecyclerview(view: View){
        view.homeFilterRv.setHasFixedSize(true)
        view.homeFilterRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        homeFilterAdapter = HomeFilterAdapter(filterItems, context!!)
        homeFilterAdapter.setItemClickListener(this)
        view.homeFilterRv.adapter = homeFilterAdapter
    }

    fun settingHomeRecyclerview(view: View){
        view.courseList.setHasFixedSize(true)
        view.courseList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        homeCourseAdapter = HomeCourseAdapter(courseItems, context!!)
        homeCourseAdapter.setOnItemClick(this)
        view.courseList.adapter = homeCourseAdapter
    }
}