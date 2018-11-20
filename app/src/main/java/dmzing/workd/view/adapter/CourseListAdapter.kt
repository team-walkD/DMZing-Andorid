package dmzing.workd.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import dmzing.workd.R
import dmzing.workd.model.map.CourseMainDto
import kotlinx.android.synthetic.main.map_course_item.view.*

class CourseListAdapter(var itemList : ArrayList<CourseMainDto>,var context : Context) : RecyclerView.Adapter<CourseListAdapter.CourseListViewHolder>() {
    var itemClick : ItemClick? = null
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CourseListViewHolder {
        var view = LayoutInflater.from(p0.context).inflate(R.layout.map_course_item,p0,false)

        return CourseListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(p0: CourseListViewHolder, p1: Int) {
        if(!itemList.get(p1).isPurchased){ //구매 X
            p0.courseLockLayout.visibility = View.VISIBLE
            p0.coursePrice.text = itemList.get(p1).price.toString() + "DP"
            p0.courseSee.isClickable = false
            p0.courseLock.setOnClickListener { v: View? ->
                val click = itemClick
                if(click != null){
                    click.OnClick(p0.itemView,p1)
                }
            }
        }
        p0.courseSubDescription.text = itemList.get(p1).subDescription
        p0.courseTitle.text = itemList.get(p1).title
        p0.courseIndicator.text = (p1+1).toString()+"/"+itemList.size
        Glide.with(context).load(itemList.get(p1).imageUrl).into(p0.courseImage)
    }

    fun SetOnLockClickListener(click : ItemClick){
        itemClick = click
    }

    interface ItemClick{
        fun OnClick(view : View,position : Int)
    }

    inner class CourseListViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var courseSubDescription : TextView = itemView.findViewById(R.id.map_course_subDescrip)
        var courseTitle : TextView = itemView.findViewById(R.id.map_course_title)
        var courseIndicator : TextView = itemView.findViewById(R.id.map_course_indicator)
        var courseImage : ImageView = itemView.findViewById(R.id.map_course_image)
        var courseSee : ImageView = itemView.findViewById(R.id.map_course_see_course)
        var courseLockLayout : RelativeLayout = itemView.findViewById(R.id.map_course_lock_layout)
        var courseLock : ImageView = itemView.findViewById(R.id.map_course_lock)
        var coursePrice : TextView = itemView.findViewById(R.id.map_course_price)
    }
}