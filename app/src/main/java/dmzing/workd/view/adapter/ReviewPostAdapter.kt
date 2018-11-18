package dmzing.workd.view.adapter

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SnapHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.rbrooks.indefinitepagerindicator.IndefinitePagerIndicator
import dmzing.workd.R
import dmzing.workd.model.review.PostDto
import kotlinx.android.synthetic.main.review_post_item.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ReviewPostAdapter(var itemList : ArrayList<PostDto>,var startDate : ArrayList<Long>,var context : Context) : RecyclerView.Adapter<ReviewPostAdapter.ReviewPostViewHolder>(){
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ReviewPostViewHolder {
        var view = LayoutInflater.from(p0.context).inflate(R.layout.review_post_item,p0,false)

        return ReviewPostViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(p0: ReviewPostViewHolder, p1: Int) {


        p0.postDay.text = itemList.get(p1).day.toString()
        p0.postDate.text = timeStampToDate(startDate.get(p1),itemList.get(p1).day)
        p0.postTitle.text = itemList.get(p1).title
        p0.postContent.text = itemList.get(p1).content


        if(itemList.get(p1).postImgUrl!!.size != 0){
            var imageAdapter = ReviewPostImageAdapter(itemList.get(p1).postImgUrl,context)
            var snapHelper: SnapHelper = PagerSnapHelper()
            snapHelper.attachToRecyclerView(p0.postImageRecycler)
            p0.postImageRecycler.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            p0.postImageRecycler.adapter = imageAdapter
            p0.postImageRecyclerIndicatior.attachToRecyclerView(p0.postImageRecycler)
        }

    }

    fun timeStampToDate(timeStamp : Long,day : Int) : String{
        var date : Date = Date(timeStamp)
        var calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.DAY_OF_MONTH,day-1)
        var dateF : SimpleDateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
        return dateF.format(calendar.time)
    }

    inner class ReviewPostViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var postDay : TextView = itemView.findViewById(R.id.review_post_day)
        var postDate : TextView = itemView.findViewById(R.id.review_post_date)
        var postTitle : TextView = itemView.findViewById(R.id.review_post_title)
        var postContent : TextView = itemView.findViewById(R.id.review_post_content)
        var postImageRecycler : RecyclerView = itemView.findViewById(R.id.review_post_image_recycler)
        var postImageRecyclerIndicatior : IndefinitePagerIndicator = itemView.findViewById(R.id.review_post_image_indicator)
    }
}