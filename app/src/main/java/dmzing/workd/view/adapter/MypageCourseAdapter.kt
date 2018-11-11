package dmzing.workd.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import dmzing.workd.R
import dmzing.workd.model.mypage.MypageCourseData
import kotlinx.android.synthetic.main.my_course_item.view.*

class MypageCourseAdapter(var itemList : ArrayList<MypageCourseData>, var context : Context) : RecyclerView.Adapter<MypageCourseAdapter.MypageCourseViewHolder>(){
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MypageCourseViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.my_course_item,p0,false)

        return MypageCourseViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(p0: MypageCourseViewHolder, p1: Int) {
        p0.courseTextView.text = itemList.get(p1).courseText
        p0.titleTextView.text = itemList.get(p1).courseTitle
        p0.imageButton.setOnClickListener {
            //context.startActivity()
        }
    }


    inner class MypageCourseViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var courseTextView : TextView = itemView.findViewById(R.id.myCourse_item_course)
        var titleTextView : TextView = itemView.findViewById(R.id.myCourse_item_title)
        var imageButton : ImageView = itemView.findViewById(R.id.mycourse_item_button)
    }
}