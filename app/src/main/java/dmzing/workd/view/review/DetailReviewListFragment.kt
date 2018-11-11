package dmzing.workd.view.review

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import dmzing.workd.R
import dmzing.workd.model.review.DetailReviewModel
import dmzing.workd.view.adapter.DetailReviewListAdpater
import kotlinx.android.synthetic.main.fragment_detail_review_list.view.*

class DetailReviewListFragment : Fragment() {
    lateinit var detailReviewList : ArrayList<DetailReviewModel>
    lateinit var detailReviewListAdpater: DetailReviewListAdpater
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_detail_review_list, container,false)

        detailReviewList = ArrayList()
        detailReviewList.add(DetailReviewModel(1,"DMZing 후기","",1541922011442L,1,1541922011442L,
            1541922011442L,true,10))

        detailReviewList.add(DetailReviewModel(2,"처음으로\n 부모님과 떠난 여행","",15203333,1,1522222,
                1533333,true,13))

        detailReviewList.add(DetailReviewModel(3,"처음으로\n 부모님과 떠난 여행","",15203333,1,1522222,
                1533333,true,5))

        detailReviewListAdpater = DetailReviewListAdpater(detailReviewList,activity!!.applicationContext)
        detailReviewListAdpater.setOnItemClickListener(object : DetailReviewListAdpater.ItemClick{
            override fun onClick(view: View, position: Int) {
                Toast.makeText(context,position.toString(),Toast.LENGTH_LONG).show()
            }

        })

        view.review_list_detail_recycler.layoutManager = LinearLayoutManager(activity)

        view.review_list_detail_recycler.adapter = detailReviewListAdpater

        return view
    }
}