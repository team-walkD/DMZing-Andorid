package dmzing.workd.view.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.skt.Tmap.TMapData
import com.skt.Tmap.TMapPoint
import dmzing.workd.R
import dmzing.workd.model.map.PlaceDto
import dmzing.workd.view.map.CoursePlaceActivity
import kotlinx.android.synthetic.main.activity_course_detail.*
import kotlinx.android.synthetic.main.course_detail_detail_item.view.*
import org.w3c.dom.Document

class CourseDetailPlaceAdapter(var itemList : ArrayList<PlaceDto>,var courseTimeList : IntArray,var context : Context) : RecyclerView.Adapter<CourseDetailPlaceAdapter.CourseDetailPlaceViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CourseDetailPlaceViewHolder {
        var view = LayoutInflater.from(p0.context).inflate(R.layout.course_detail_detail_item,p0,false)

        return CourseDetailPlaceViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(p0: CourseDetailPlaceViewHolder, p1: Int) {
        Log.d("aaaa","adapter bind")
        p0.courseDetailPlaceTitle.text = itemList.get(p1).title
        p0.courseDetailPlaceSequence.text = (p1+1).toString()
        Glide.with(context).load(itemList.get(p1).mainImageUrl).apply(RequestOptions().centerCrop()).into(p0.courseDetailplaceImage)
        p0.courseDetailplaceImage.setOnClickListener {
            var intent = Intent(context,CoursePlaceActivity::class.java)
            intent.putExtra("place",itemList.get(p1))
            context.startActivity(intent)

        }
        for(i in 0 until itemList.get(p1).peripheries!!.size){
            when(i){
                0->{
                    Glide.with(context).load(itemList.get(p1).peripheries!!.get(i).firstimage).apply(RequestOptions().centerCrop()).into(p0.courseDetailPlacePeriOneImage)
                    p0.courseDetailPlacePeriOneText.text = itemList.get(p1).peripheries!!.get(i).title
                }
                1->{
                    Glide.with(context).load(itemList.get(p1).peripheries!!.get(i).firstimage).apply(RequestOptions().centerCrop()).into(p0.courseDetailPlacePeriTwoImage)
                    p0.courseDetailPlacePeriTwoText.text = itemList.get(p1).peripheries!!.get(i).title
                }
                2->{
                    Glide.with(context).load(itemList.get(p1).peripheries!!.get(i).firstimage).apply(RequestOptions().centerCrop()).into(p0.courseDetailPlacePeriThreeImage)
                    p0.courseDetailPlacePeriThreeText.text = itemList.get(p1).peripheries!!.get(i).title
                }
            }
        }
        if(p1 < itemList.size-1){
            //setEstimatedTime(p1,p0)
            p0.courseDetailPlaceTime.text = "차 "+(courseTimeList.get(p1)/60).toString()+"분"
        } else {
            p0.courseDetailBlueLine.visibility = View.INVISIBLE
        }
    }

    fun setEstimatedTime(position : Int,view : CourseDetailPlaceViewHolder){
        var mapPoint = TMapPoint(itemList.get(position).latitude,itemList.get(position).longitude)
            var destPoint = TMapPoint(itemList.get(position+1).latitude,itemList.get(position+1).longitude)
            var tMapData = TMapData()
            var test = tMapData.findPathDataAllType(TMapData.TMapPathType.CAR_PATH,mapPoint,destPoint,object : TMapData.FindPathDataAllListenerCallback{
                override fun onFindPathDataAll(p0: Document?) {
                    var element = p0!!.documentElement
                    val list = element.getElementsByTagName("tmap:totalTime")
                    Log.d("aaaa","adapter : "+list.item(0).textContent)
                    view.courseDetailPlaceTime.text = "차 "+(list.item(0).textContent.toInt()/60).toString()+"분"
                }
            })
    }

    inner class CourseDetailPlaceViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var courseDetailPlaceTitle : TextView = itemView.findViewById(R.id.course_detail_detail_title)
        var courseDetailplaceImage : ImageView = itemView.findViewById(R.id.course_detail_detail_image)
        var courseDetailPlaceSequence : TextView = itemView.findViewById(R.id.course_detail_detail_sequence)
        var courseDetailPlacePeriOneImage : ImageView = itemView.findViewById(R.id.course_detail_detail_peri_image1)
        var courseDetailPlacePeriOneText : TextView = itemView.findViewById(R.id.course_detail_detail_peri_title1)
        var courseDetailPlacePeriTwoImage : ImageView = itemView.findViewById(R.id.course_detail_detail_peri_image2)
        var courseDetailPlacePeriTwoText : TextView = itemView.findViewById(R.id.course_detail_detail_peri_title2)
        var courseDetailPlacePeriThreeImage : ImageView = itemView.findViewById(R.id.course_detail_detail_peri_image3)
        var courseDetailPlacePeriThreeText : TextView = itemView.findViewById(R.id.course_detail_detail_peri_title3)
        var courseDetailPlaceTime : TextView = itemView.findViewById(R.id.course_detail_detail_time)
        var courseDetailBlueLine : View = itemView.findViewById(R.id.course_detail_detail_blue_line)
    }
}