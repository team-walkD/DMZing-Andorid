package dmzing.workd.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import dmzing.workd.R
import kotlinx.android.synthetic.main.activity_review_write.view.*

class ReviewWriteDayAdapter(var itemList : ArrayList<String>, var context : Context) : RecyclerView.Adapter<ReviewWriteDayAdapter.ReviewWriteDayViewHolder>(){

    var itemClick : ItemClick? = null
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ReviewWriteDayViewHolder {
        var view = LayoutInflater.from(p0!!.context).inflate(R.layout.review_day_select_item, p0, false)

        return ReviewWriteDayViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(p0: ReviewWriteDayViewHolder, p1: Int) {
        p0.reviewWriteDay.text = itemList.get(p1)
        p0.itemView.setOnClickListener {
            val click = itemClick
            if(click != null){
                click.onClick(p0.itemView,p1,p0.reviewWriteDay)
            }
        }
    }

    fun setItemClickListener(click : ItemClick){
        itemClick = click
    }

    inner class ReviewWriteDayViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var reviewWriteDay : TextView = itemView.findViewById(R.id.review_day_select_text)
    }

    interface ItemClick{
        fun onClick(view : View,position : Int,dayText : TextView)
    }
}