package dmzing.workd.view.mypage

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import dmzing.workd.R
import dmzing.workd.model.mypage.MypageReviewData
import dmzing.workd.view.adapter.MypageReviewAdapter
import kotlinx.android.synthetic.main.activity_mypage_review.*

class MypageReviewActivity : AppCompatActivity() {

    lateinit var reviewItems : ArrayList<MypageReviewData>
    lateinit var mypageReviewAdapter : MypageReviewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage_review)
        reviewItems = ArrayList()
        for(i in 0..10)
            reviewItems.add(MypageReviewData("오늘의 장소는~~","2018.03.03"))


        reviewNested.scrollTo(0,0)
        reviewList.setHasFixedSize(true)
        mypageReviewAdapter = MypageReviewAdapter(reviewItems, this)
        reviewList.layoutManager = LinearLayoutManager(this)
        reviewList.adapter = mypageReviewAdapter



    }
}
