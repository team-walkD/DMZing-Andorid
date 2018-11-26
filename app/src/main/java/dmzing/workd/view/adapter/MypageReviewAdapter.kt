package dmzing.workd.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import dmzing.workd.R
import dmzing.workd.model.mypage.MypageReviewData
import dmzing.workd.model.mypage.review.GetMypageReviewData
import kotlinx.android.synthetic.main.item_review_list.view.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by VictoryWoo
 */
class MypageReviewAdapter(var items : ArrayList<GetMypageReviewData>, var context : Context)
    : RecyclerView.Adapter<MypageReviewAdapter.MypageReviewViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MypageReviewViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_review_list, parent, false)
        return MypageReviewViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: MypageReviewViewHolder, position: Int) {
        Glide.with(context).load(R.drawable.my_myreview_icon).into(holder.reviewImage)
        holder.reviewTitle.text = items[position].title
        holder.reviewDate.text = timeStampToDate(items[position].createdAt)

    }

    inner class MypageReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var reviewTitle: TextView = itemView.reviewTitleItem
        var reviewDate: TextView = itemView.reviewDateItem
        var reviewImage : ImageView = itemView.reviewImageItem
    }

    fun timeStampToDate(timeStamp : Long) : String{
        var date : Date = Date(timeStamp)
        var dateF : SimpleDateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
        return dateF.format(date)
    }
}