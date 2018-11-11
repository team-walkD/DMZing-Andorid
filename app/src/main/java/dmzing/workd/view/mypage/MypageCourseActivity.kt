package dmzing.workd.view.mypage

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import dmzing.workd.R
import dmzing.workd.model.mypage.MypageCourseData
import dmzing.workd.view.adapter.MypageCourseAdapter
import kotlinx.android.synthetic.main.activity_mypage_course.*

class MypageCourseActivity : AppCompatActivity() {

    lateinit var myCourseList : ArrayList<MypageCourseData>
    lateinit var myCourseAdapter : MypageCourseAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage_course)

        myCourseList = ArrayList()
        for(i in 0..10)
            myCourseList.add(MypageCourseData("사진찍기좋은 우오아","데이트하고싶다"))

        myCourseAdapter = MypageCourseAdapter(myCourseList,this)

        myCourseRecycler.layoutManager = LinearLayoutManager(this)
        myCourseRecycler.adapter = myCourseAdapter
    }
}
