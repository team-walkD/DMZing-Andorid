package dmzing.workd.view.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dmzing.workd.R
import dmzing.workd.model.review.SimpleReviewDto
import java.text.SimpleDateFormat
import java.util.*

class DetailReviewListAdpater(var itemList : ArrayList<SimpleReviewDto>,var context : Context) : RecyclerView.Adapter<DetailReviewListAdpater.DetailReviewListViewHolder>() {
    lateinit var itemClick : ItemClick
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): DetailReviewListViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.detail_review_item,p0,false)

        return DetailReviewListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(p0: DetailReviewListViewHolder, p1: Int) {
        if(itemList.get(p1).thumbnailUrl.equals("") || itemList.get(p1).thumbnailUrl == null){
            p0.detailReviewImage.setBackgroundColor(Color.parseColor("#e3e3e3"))
        } else {
            Log.d("zxcv","zxcv")
            Glide.with(context).load(itemList.get(p1).thumbnailUrl).apply(RequestOptions().centerCrop()).into(p0.detailReviewImage)
            p0.detailReviewImage.setColorFilter(PorterDuffColorFilter(Color.parseColor("#7f000000"),PorterDuff.Mode.SRC_OVER))
            p0.detailReviewImage.clipToOutline = true
        }
        p0.detailReviewTitle.text = itemList.get(p1).title
        p0.detailReviewStartDate.text = timeStampToDate(itemList.get(p1).startAt!!)
        p0.detailReviewEndDate.text = timeStampToDate(itemList.get(p1).endAt!!)
        if(itemList.get(p1).like!!){
            p0.detailReviewLike.visibility = View.VISIBLE
        } else {
            p0.detailReviewLike.visibility = View.GONE
        }
        p0.detailReviewLikeCnt.text = itemList.get(p1).likeCount.toString()+"k"

        p0.itemView.setOnClickListener { v: View? ->
            if(itemClick != null){
                var click = itemClick
                click!!.onClick(v!!,p1)
            }
        }
    }

    fun setOnItemClickListener(click : ItemClick){
        itemClick = click
    }

    fun timeStampToDate(timeStamp : Long) : String{
        var date : Date = Date(timeStamp)
        var dateF : SimpleDateFormat = SimpleDateFormat("yyyy.mm.dd", Locale.getDefault())
        return dateF.format(date)
    }

    interface ItemClick{
        fun onClick(view : View,position : Int)
    }

    class DetailReviewListViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        var detailReviewImage : ImageView = itemView.findViewById(R.id.detail_list_image_item)
        var detailReviewTitle : TextView = itemView.findViewById(R.id.detail_list_title_item)
        var detailReviewStartDate : TextView = itemView.findViewById(R.id.detail_list_start_date_item)
        var detailReviewEndDate : TextView = itemView.findViewById(R.id.detail_list_end_date_item)
        var detailReviewSoloDate : TextView = itemView.findViewById(R.id.detail_list_solo_date_item)
        var detailReviewLike : ImageView = itemView.findViewById(R.id.detail_list_like_item)
        var detailReviewLikeCnt : TextView = itemView.findViewById(R.id.detail_list_like_cnt_item)
        var detailReviewDash : View = itemView.findViewById(R.id.detail_list_dash_item)
    }
}