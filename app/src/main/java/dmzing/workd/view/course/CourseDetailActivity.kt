package dmzing.workd.view.course

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.BitmapDrawable
import android.icu.util.Measure
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.skt.Tmap.*
import dmzing.workd.R
import dmzing.workd.model.map.CourseDetailDto
import dmzing.workd.util.SpaceItemDecoration
import dmzing.workd.view.adapter.CourseDetailPlaceAdapter
import dmzing.workd.view.adapter.CourseDetailSimplePlaceAdapter
import kotlinx.android.synthetic.main.activity_course_detail.*
import kotlinx.android.synthetic.main.course_detail_detail_item.*
import org.jetbrains.anko.*
import org.jetbrains.anko.coroutines.experimental.Ref
import org.jetbrains.anko.coroutines.experimental.asReference
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.custom.async
import org.w3c.dom.Document
import java.lang.Exception

class CourseDetailActivity : AppCompatActivity() {

    lateinit var courseDetailDto : CourseDetailDto
    lateinit var courseTimeList : ArrayList<Int>
    lateinit var courseTimeTotal : ArrayList<Int>
    lateinit var arry : IntArray

    lateinit var courseDetailAdapter : CourseDetailPlaceAdapter
    lateinit var courseSimpleTime : TextView
    lateinit var courseDetailRecycler : RecyclerView
    val TMAP_KEY = "90b70b30-07bb-4f12-a57c-a149df078b02"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_detail)
        courseDetailDto = intent.extras.get("courseDetailDto") as CourseDetailDto
        courseTimeList = ArrayList()
        courseTimeTotal = ArrayList()
        arry = IntArray(courseDetailDto.places!!.size-1)

        courseSimpleTime = findViewById(R.id.course_detail_calendar_simple_totalTime)
        courseDetailRecycler = findViewById(R.id.course_detail_detail_recycler)
        var index = intent.getIntExtra("idx",0)
        //toast("${index}")

        setViewData()

        setSimpleCalendar()
        setSimpleTmap()
        setRouteTime()
        course_detail_back.setOnClickListener {
            finish()
        }
        course_detail_calendar_see_detail.setOnClickListener {
            course_detail_calendar_simple.visibility = View.GONE
            course_detail_calendar_detail.visibility = View.VISIBLE
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

            val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = wm.defaultDisplay
            val size = Point()
            display.getSize(size)

            var param = course_detail_blue_line4.layoutParams as RelativeLayout.LayoutParams
            param.setMargins(size.x/8,size.y/46,size.x/8,0)
            course_detail_blue_line4.layoutParams = param

            var param1 = course_detail_marker3.layoutParams as RelativeLayout.LayoutParams
            param1.setMargins(0,0,size.x/6,0)
            course_detail_marker3.layoutParams = param1

            var param2 = course_detail_marker2.layoutParams as RelativeLayout.LayoutParams
            param2.setMargins(size.x/6,0,0,0)
            course_detail_marker2.layoutParams = param2
        }
        val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val size = Point()
        display.getSize(size)
        course_detail_calendar_simple_recycler.addItemDecoration(SpaceItemDecoration(size.x/45,size.x/17))

        course_detail_calendar_simple_recycler.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        course_detail_calendar_simple_recycler.adapter = courseDetailSimpleAdapter

    }

    fun setSimpleTmap(){
        var placeList = courseDetailDto.places
        var mTmapView = TMapView(this)
        var detailTmapView = TMapView(this)
        mTmapView.setSKTMapApiKey(TMAP_KEY)
        mTmapView.setLanguage(TMapView.LANGUAGE_KOREAN)
        mTmapView.setCenterPoint(placeList!!.get(0).longitude,placeList!!.get(0).latitude)
        mTmapView.zoomLevel = 12

        detailTmapView.setSKTMapApiKey(TMAP_KEY)
        detailTmapView.setLanguage(TMapView.LANGUAGE_KOREAN)
        detailTmapView.setCenterPoint(placeList!!.get(0).longitude,placeList!!.get(0).latitude)
        detailTmapView.zoomLevel = 12

        for(i in 0 until placeList!!.size){
            //view를 bitmap으로 변환
            var view = layoutInflater.inflate(R.layout.tmap_marker_item,null)
            var marker : RelativeLayout = view.findViewById(R.id.tmap_marker)
            var sequence : TextView = view.findViewById(R.id.tmap_marker_num)
            sequence.text = (i+1).toString()
            marker.isDrawingCacheEnabled = true
            marker.measure(View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED),View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED))
            marker.layout(0, 0, marker.getMeasuredWidth(), marker.getMeasuredHeight())
            marker.buildDrawingCache()
            var markerBitmap = Bitmap.createBitmap(marker.drawingCache)
            marker.isDrawingCacheEnabled = false

            //마커 및 현재 좌표 설정
            var mapPoint = TMapPoint(placeList.get(i).latitude,placeList.get(i).longitude)
            var tmapMarkerItem = TMapMarkerItem()
            tmapMarkerItem.icon = markerBitmap
            tmapMarkerItem.setPosition(0.5f,1.0f)
            tmapMarkerItem.tMapPoint = mapPoint
            mTmapView.addMarkerItem(placeList.get(i).title,tmapMarkerItem)
            detailTmapView.addMarkerItem(placeList.get(i).title,tmapMarkerItem)

            //var courseTimeList : ArrayList<Int> = ArrayList()
            //각 지점간 소요 시간

        }
        //경로 그리기
        doAsync {
            for(i in 0 until placeList.size){
                var mapPoint = TMapPoint(placeList.get(i).latitude,placeList.get(i).longitude)
                if(i < placeList!!.size - 1){
                    var destPoint = TMapPoint(placeList.get(i+1).latitude,placeList.get(i+1).longitude)
                    var tMapData = TMapData()
                    tMapData.findPathDataWithType(TMapData.TMapPathType.CAR_PATH,mapPoint,destPoint,object : TMapData.FindPathDataListenerCallback{
                        override fun onFindPathData(p0: TMapPolyLine?) {
                            p0!!.lineColor = Color.parseColor("#6da8c7")
                            p0!!.lineWidth = 20f
                            mTmapView.addTMapPolyLine(placeList.get(i).id.toString()+"_simple",p0)
                            detailTmapView.addTMapPolyLine(placeList.get(i).id.toString()+"_detail",p0)
                        }

                    })
                }
            }
            uiThread {
                course_detail_detail_tmap.addView(detailTmapView)
                course_detail_calendar_simple_tmap.addView(mTmapView)
            }
        }
    }

    fun setRouteTime(){
        doAsync {
            var placeList = courseDetailDto.places
            for(i in 0 until placeList!!.size){
                //현재 좌표 설정
                var mapPoint = TMapPoint(placeList.get(i).latitude,placeList.get(i).longitude)
                //각 지점간 소요 시간
                if(i < placeList!!.size -1){
                    var destPoint = TMapPoint(placeList.get(i+1).latitude,placeList.get(i+1).longitude)
                    var tMapData = TMapData()
                    tMapData.findPathDataAllType(TMapData.TMapPathType.CAR_PATH,mapPoint,destPoint,object : TMapData.FindPathDataAllListenerCallback{
                        override fun onFindPathDataAll(p0: Document?) {
                            var element = p0!!.documentElement
                            val list = element.getElementsByTagName("tmap:totalTime")
                            var totalTime = 0
                            courseTimeTotal.add(list.item(0).textContent.toInt())
                            arry[i] = list.item(0).textContent.toInt()
                            Log.d("ffff : routeNum",i.toString())
                            Log.d("ffff : routeTime",i.toString()+"aa"+arry[i].toString())
                        }

                    })
                }

            }
            uiThread {
                courseDetailAdapter = CourseDetailPlaceAdapter(placeList,arry,applicationContext)
                courseDetailRecycler.layoutManager = LinearLayoutManager(applicationContext)
                courseDetailRecycler.adapter = courseDetailAdapter
                course_detail_calendar_simple_totalTime.text = courseDetailDto.estimatedTime.toString()+"시간"
            }
        }
    }
}
