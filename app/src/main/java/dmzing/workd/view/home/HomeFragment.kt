package dmzing.workd.view.home

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dmzing.workd.R
import dmzing.workd.model.home.HomeCourseData
import dmzing.workd.view.adapter.HomeCourseAdapter
import kotlinx.android.synthetic.main.fragment_home.view.*

/**
 * Created by VictoryWoo
 */
class HomeFragment : Fragment() {

    lateinit var homeCourseAdapter: HomeCourseAdapter
    lateinit var courseItems : ArrayList<HomeCourseData>
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_home, container, false)



        courseItems = ArrayList()
        for(i in 0..5)
            courseItems.add(HomeCourseData(R.drawable.demo_image, "중","3","50"))


        view.courseList.setHasFixedSize(true)
        view.courseList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        homeCourseAdapter = HomeCourseAdapter(courseItems,context!!)
        view.courseList.adapter = homeCourseAdapter

        return view
    }
}