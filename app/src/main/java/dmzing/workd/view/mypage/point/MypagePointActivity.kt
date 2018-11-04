package dmzing.workd.view.mypage.point

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import dmzing.workd.R
import dmzing.workd.model.mypage.MypagePoint
import dmzing.workd.util.Utils
import dmzing.workd.view.adapter.MypagePointAdapter
import kotlinx.android.synthetic.main.activity_mypage_point.*

class MypagePointActivity : AppCompatActivity(), View.OnClickListener, Utils {

    override fun init() {
        pointBackBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!) {
            pointBackBtn -> finish()
        }
    }


    lateinit var pointItems: ArrayList<MypagePoint>
    lateinit var mypagePointAdapter: MypagePointAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage_point)

        pointItems = ArrayList()
        for (i in 0..10)
            pointItems.add(MypagePoint("데이트 맵", "-1,000", "10.17(Tue)", "차감"))

        mypagePointAdapter = MypagePointAdapter(pointItems, this)

        pointListRecyclerview.adapter = mypagePointAdapter
        pointListRecyclerview.layoutManager = LinearLayoutManager(this)


    }
}
