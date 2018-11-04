package dmzing.workd.view.review

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import dmzing.workd.R
import kotlinx.android.synthetic.main.fragment_review.view.*
import java.util.ArrayList

class ReviewFragment : Fragment() {
    lateinit var mapItemList : ArrayList<ReviewMapModel>
    lateinit var gridLayoutManager: GridLayoutManager
    lateinit var mapItemAdapter : ReviewMapAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_review, container,false)

        mapItemList = ArrayList()
        mapItemList.add(ReviewMapModel("","데이트 맵","05"))
        mapItemList.add(ReviewMapModel("","역사기행 맵","27"))
        mapItemList.add(ReviewMapModel("","탐험 맵","27"))
        mapItemList.add(ReviewMapModel("","COMING SOON","00"))


        gridLayoutManager = GridLayoutManager(activity,2)
        mapItemAdapter = ReviewMapAdapter(mapItemList,activity!!.applicationContext)
        mapItemAdapter.setOnItemClickListener(object : ReviewMapAdapter.Itemclick{
            override fun onClick(view: View, position: Int) {
                Toast.makeText(activity,position.toString(),Toast.LENGTH_LONG).show()
            }

        })
        view.review_recyclerView.layoutManager = gridLayoutManager
        view.review_recyclerView.adapter = mapItemAdapter


        view.review_walkd_button.setOnClickListener { v: View->
            //챗봇 버튼
        }

        view.review_write_button.setOnClickListener { v: View ->
            //리뷰 쓰기 버튼
        }
        return view
    }

}