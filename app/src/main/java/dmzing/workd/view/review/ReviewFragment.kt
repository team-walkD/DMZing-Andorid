package dmzing.workd.view.review

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import dmzing.workd.R
import dmzing.workd.model.review.ReviewMapData
import dmzing.workd.util.GridItemDecoration
import dmzing.workd.view.adapter.ReviewMapAdapter
import dmzing.workd.view.mypage.MyLetterActivity
import kotlinx.android.synthetic.main.fragment_review.view.*
import java.util.ArrayList

class ReviewFragment : Fragment() {
    lateinit var mapItemList : ArrayList<ReviewMapData>
    lateinit var gridLayoutManager: GridLayoutManager
    lateinit var mapItemAdapter : ReviewMapAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_review, container,false)

        mapItemList = ArrayList()
        mapItemList.add(ReviewMapData("", "데이트 맵", "05"))
        mapItemList.add(ReviewMapData("", "역사기행 맵", "27"))
        mapItemList.add(ReviewMapData("", "탐험 맵", "27"))
        mapItemList.add(ReviewMapData("", "COMING SOON", "00"))


        gridLayoutManager = GridLayoutManager(activity,2)
        mapItemAdapter = ReviewMapAdapter(mapItemList, activity!!.applicationContext)
        mapItemAdapter.setOnItemClickListener(object : ReviewMapAdapter.Itemclick{
            override fun onClick(view: View, position: Int) {
                Toast.makeText(activity,position.toString(),Toast.LENGTH_LONG).show()
                startActivity(Intent(activity,MyLetterActivity::class.java))
            }

        })
        view.review_recyclerView.layoutManager = gridLayoutManager
        view.review_recyclerView.adapter = mapItemAdapter

        var px = Math.round(convertDpToPixel(28f,activity!!))
        view.review_recyclerView.addItemDecoration(GridItemDecoration(px))



        view.review_walkd_button.setOnClickListener { v: View->
            //챗봇 버튼
        }

        view.review_write_button.setOnClickListener { v: View ->
            //리뷰 쓰기 버튼
        }
        return view
    }

    fun convertDpToPixel(dp : Float,context : Context) : Float{
        var resources = context.resources

        var metrics = resources.displayMetrics

        var px = dp * (metrics.densityDpi / 160f)

        return px
    }

}