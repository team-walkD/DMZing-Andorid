package dmzing.workd.view.course

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.skt.Tmap.*
import dmzing.workd.R
import dmzing.workd.model.map.CourseDetailDto
import dmzing.workd.view.adapter.CourseDetailSimplePlaceAdapter
import kotlinx.android.synthetic.main.activity_course_detail.*
import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import org.w3c.dom.Document
import java.lang.Exception

class CourseDetailActivity : AppCompatActivity() {

    lateinit var courseDetailDto : CourseDetailDto
    val TMAP_KEY = "90b70b30-07bb-4f12-a57c-a149df078b02"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_detail)

        courseDetailDto = intent.extras.get("courseDetailDto") as CourseDetailDto
        var index = intent.getIntExtra("idx",0)
        //toast("${index}")

        setViewData()

        setSimpleCalendar()

        setTmap()


        course_detail_back.setOnClickListener {
            finish()
        }
        course_detail_walk_d.setOnClickListener {

        }
    }

    fun setViewData(){
        Glide.with(this).load(courseDetailDto.imageUrl).apply(RequestOptions().centerCrop()).into(course_detail_mainimage)
        course_detail_subdescrip.text = courseDetailDto.subDescription
        course_detail_title.text = courseDetailDto.title
        course_detail_title_explain.text = courseDetailDto.title+"란?"
        course_detail_maindescrip.text = courseDetailDto.mainDescription
        course_detail_level.text = courseDetailDto.level
        course_detail_estimatedTime.text = courseDetailDto.estimatedTime.toString()
        course_detail_review_count.text = courseDetailDto.reviewCount.toString()
    }

    fun setSimpleCalendar(){
        var placesList = courseDetailDto.places
        var courseDetailSimpleAdapter : CourseDetailSimplePlaceAdapter = CourseDetailSimplePlaceAdapter(placesList!!,this)

        if(placesList.size == 3){
            course_detail_blue_line3.visibility = View.VISIBLE
            course_detail_blue_line4.visibility = View.GONE
        } else if(placesList.size == 4){
            course_detail_blue_line3.visibility = View.GONE
            course_detail_blue_line4.visibility = View.VISIBLE
        }
        course_detail_calendar_simple_recycler.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        course_detail_calendar_simple_recycler.adapter = courseDetailSimpleAdapter

//        for(i in 0 until course_detail_calendar_simple_recycler.childCount){
//            if(i != course_detail_calendar_simple_recycler.childCount-1){
//                var params = course_detail_calendar_simple_recycler.layoutParams as LinearLayout.LayoutParams
//                params.rightMargin = 100
//                course_detail_calendar_simple_recycler.getChildAt(i).layoutParams = params
//            }
//        }
    }

    fun setTmap(){
        var placeList = courseDetailDto.places
        var mTmapView = TMapView(this)
        mTmapView.setSKTMapApiKey(TMAP_KEY)
        mTmapView.setLanguage(TMapView.LANGUAGE_KOREAN)
        mTmapView.setCenterPoint(placeList!!.get(0).longitude,placeList!!.get(0).latitude)
        mTmapView.zoomLevel = 12

        for(i in 0 until placeList!!.size){
//            var marker : TextView = find(R.id.tmap_marker_num)
//            marker.text = (i+1).toString()
//            marker.isDrawingCacheEnabled = true
//            var markerBitmap = Bitmap.createBitmap(marker.drawingCache)
            var markerBitmap = BitmapFactory.decodeResource(this.resources,R.drawable.heart_filled_icon)

            var mapPoint = TMapPoint(placeList.get(i).latitude,placeList.get(i).longitude)
            var tmapMarkerItem = TMapMarkerItem()
            tmapMarkerItem.icon = markerBitmap
            tmapMarkerItem.setPosition(0.5f,1.0f)
            tmapMarkerItem.tMapPoint = mapPoint
            mTmapView.addMarkerItem(placeList.get(i).title,tmapMarkerItem)

            if(i != placeList!!.size -1){
                var destPoint = TMapPoint(placeList.get(i+1).latitude,placeList.get(i+1).longitude)
                var tMapData = TMapData()
                tMapData.findPathDataAllType(TMapData.TMapPathType.CAR_PATH,mapPoint,destPoint,object : TMapData.FindPathDataAllListenerCallback{
                    override fun onFindPathDataAll(p0: Document?) {
                        var element = p0!!.documentElement
                        val list = element.getElementsByTagName("tmap:totalTime")
                        Log.d("aaa", ""+list.getLength())
                        for (i in 0 until list.getLength()) {
                            Log.d("aaaa", list.item(i).getTextContent())
                        }
                    }

                })
//                tMapData.findPathDataWithType(TMapData.TMapPathType.CAR_PATH,mapPoint,destPoint,object : TMapData.FindPathDataListenerCallback{
//                    override fun onFindPathData(p0: TMapPolyLine?) {
//                        p0!!.lineColor = Color.parseColor("#6da8c7")
//                        p0!!.lineWidth = 10f
//                        mTmapView.addTMapPath(p0)
//                    }
//
//                })
                var tmapPolyline = tMapData.findPathData(mapPoint,destPoint)
                tmapPolyline.lineWidth = 2f
                tmapPolyline.lineColor = Color.BLUE
                mTmapView.addTMapPolyLine("poly"+placeList.get(i).id,tmapPolyline)
            }

        }
        course_detail_calendar_simple_tmap.addView(mTmapView)
    }

    fun setDetailCalendar(){

    }
}
