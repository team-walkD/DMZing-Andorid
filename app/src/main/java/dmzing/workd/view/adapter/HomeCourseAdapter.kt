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
import dmzing.workd.model.home.HomeCourseData
import kotlinx.android.synthetic.main.home_course_item_list.view.*

/**
 * Created by VictoryWoo
 */
class HomeCourseAdapter(var item_list : ArrayList<HomeCourseData>, private var context : Context)
    : RecyclerView.Adapter<HomeCourseAdapter.HomeCourseViewHolder>() {

    private lateinit var onItemClick : View.OnClickListener

    fun setOnItemClick(l : View.OnClickListener){
        onItemClick = l
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeCourseViewHolder {
        var view = LayoutInflater.from(parent!!.context).inflate(R.layout.home_course_item_list, parent, false)
        view.setOnClickListener(onItemClick)
        return HomeCourseViewHolder(view)
    }

    override fun getItemCount(): Int = item_list.size

    override fun onBindViewHolder(holder: HomeCourseViewHolder, position: Int) {
        Glide.with(context).load(item_list[position].course_image).into(holder.course_image_item)
        holder.course_level_item.text = item_list[position].course_level
        holder.course_time_item.text = item_list[position].course_time
        holder.course_participants_item.text = item_list[position].course_participants

    }

    inner class HomeCourseViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var course_image_item : ImageView = itemView.findViewById(R.id.courseImageItem)
        var course_level_item : TextView = itemView.findViewById(R.id.courseLevelText)
        var course_time_item : TextView = itemView.findViewById(R.id.courseTimeText)
        var course_participants_item : TextView = itemView.findViewById(R.id.courseParticipantsText)

    }
}