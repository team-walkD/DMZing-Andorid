package dmzing.workd.view.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dmzing.workd.R
import dmzing.workd.model.map.PlaceDto
import dmzing.workd.view.map.CoursePlaceActivity
import kotlinx.android.synthetic.main.course_detail_simple_place_item.view.*

class CourseDetailSimplePlaceAdapter(var itemList : ArrayList<PlaceDto>,var context : Context) : RecyclerView.Adapter<CourseDetailSimplePlaceAdapter.CourseDetailSimplePlaceViewholder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CourseDetailSimplePlaceViewholder {
        var view = LayoutInflater.from(p0.context).inflate(R.layout.course_detail_simple_place_item,p0,false)

        return CourseDetailSimplePlaceViewholder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(p0: CourseDetailSimplePlaceViewholder, p1: Int) {
        Glide.with(context).load(itemList.get(p1).mainImageUrl).apply(RequestOptions().centerCrop()).into(p0.coursePlaceImage)
        p0.coursePlaceTitle.text = itemList.get(p1).title
        p0.coursePlaceTitle.isSelected = true
        p0.itemView.setOnClickListener {
            var intent = Intent(context,CoursePlaceActivity::class.java)
            intent.putExtra("place",itemList.get(p1))
            context.startActivity(intent)
        }
    }

    inner class CourseDetailSimplePlaceViewholder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var coursePlaceImage : ImageView = itemView.findViewById(R.id.course_detail_simple_place_image)
        var coursePlaceTitle : TextView = itemView.findViewById(R.id.course_detail_simple_place_title)
    }
}