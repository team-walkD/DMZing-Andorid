package dmzing.workd.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import dmzing.workd.R
import dmzing.workd.model.mypage.MypageReviewData
import dmzing.workd.model.mypage.review.GetMypageReviewData
import kotlinx.android.synthetic.main.item_review_list.view.*

/**
 * Created by VictoryWoo
 */
class MypageReviewAdapter(var items : List<GetMypageReviewData>, var context : Context)
    : RecyclerView.Adapter<MypageReviewAdapter.MypageReviewViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MypageReviewViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_review_list, parent, false)
        return MypageReviewViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: MypageReviewViewHolder, position: Int) {

    }

    inner class MypageReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var review_title: TextView = itemView.findViewById(R.id.reviewTitleItem)
        var review_date: TextView = itemView.findViewById(R.id.reviewDateItem)
    }
}