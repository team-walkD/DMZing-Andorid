package dmzing.workd.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import dmzing.workd.CommonData
import dmzing.workd.R
import dmzing.workd.model.home.HomePostMission
import dmzing.workd.model.home.PickCourse
import dmzing.workd.model.home.Places
import dmzing.workd.network.ApplicationController
import dmzing.workd.network.NetworkService
import dmzing.workd.util.SharedPreference
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by VictoryWoo
 */
class HomeCourseAdapter(var item_list: PickCourse, private var context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var onItemClick: View.OnClickListener
    lateinit var networkService: NetworkService
    lateinit var homePostMission: HomePostMission
    private val TYPE_HEADER = 0
    private val TYPE_LETTER = 1

    companion object {
        val LATITUDE = 38.5763238583
        val LONGTITUE = 128.3826570629
    }


    fun setOnItemClick(l: View.OnClickListener) {
        onItemClick = l
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_HEADER) {
            var headerView = LayoutInflater.from(parent.context)
                .inflate(R.layout.home_header_course_item_list, parent, false)
            return HeaderCourseViewHolder(headerView)
        } else {
            var letterView = LayoutInflater.from(parent!!.context)
                .inflate(R.layout.home_letter_item_list, parent, false)
            letterView.setOnClickListener(onItemClick)
            return LetterCourseViewHolder(letterView)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (isPositionHeader(position))
            return TYPE_HEADER
        else
            return TYPE_LETTER
    }

    fun isPositionHeader(position: Int): Boolean {
        return position == TYPE_HEADER
    }

    override fun getItemCount(): Int = item_list.places.size + 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HeaderCourseViewHolder) {
            var headerCourseViewHolder: HeaderCourseViewHolder = holder

            headerCourseViewHolder.courseTitle.text = item_list.title
            headerCourseViewHolder.courseSubTitle.text = item_list.subDescription
            Glide.with(context).load(item_list.imageUrl).into(headerCourseViewHolder.headerCourseImage)
            headerCourseViewHolder.courseLevel.text = item_list.level
            headerCourseViewHolder.courseTime.text = item_list.estimatedTime
            // 코스 아이디 저장
            CommonData.coursedId = item_list.id
            headerCourseViewHolder.courseParticipants.text = item_list.reviewCount.toString()
            headerCourseViewHolder.headerCourseButton.setOnClickListener {
                context.toast("코스 상세 보기 버튼!")
            }

        } else if (holder is LetterCourseViewHolder) {
            var letterCourseViewHolder: LetterCourseViewHolder = holder
            letterCourseViewHolder.letterTitle.text = item_list.places[position-1].title
            //letterCourseViewHolder.letterSubTitle.text = item_list.places[position-1].description
            // 플레이스 아이디 저장
            CommonData.placeId = item_list.places[position-1].id
            Glide.with(context).load(item_list.places[position-1].mainImageUrl).into(letterCourseViewHolder.letterImage)
            letterCourseViewHolder.letterHint.text = item_list.places[position-1].hint
            letterCourseViewHolder.letterButton.setOnClickListener {
                context.toast("편지 찾기 버튼!")
                postMission()
            }

        }


    }

    // 편지 찾기 post 통신
    fun postMission(){
        networkService = ApplicationController.instance.networkService
        SharedPreference.instance!!.load(context)
        homePostMission = HomePostMission(CommonData.coursedId, CommonData.placeId, LATITUDE, LONGTITUE)
        var jwt = SharedPreference.instance!!.getPrefStringData(CommonData.JWT)
        var missionResponse = networkService.postMission(jwt!!,homePostMission)
        missionResponse.enqueue(object : Callback<ArrayList<Places>>{
            override fun onFailure(call: Call<ArrayList<Places>>, t: Throwable) {
                Log.v("110 woo f:",t.message)
            }

            override fun onResponse(call: Call<ArrayList<Places>>, response: Response<ArrayList<Places>>) {
                Log.v("110 woo r:",response.code().toString())
                Log.v("110 woo r:",response.body().toString())
                when(response.code()){
                    200->{

                    }
                }

            }

        })
    }


    inner class HeaderCourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var courseTitle: TextView = itemView.findViewById(R.id.headerCourseTitleItem)
        var courseSubTitle: TextView = itemView.findViewById(R.id.headerCourseSubTitleItem)
        var courseLevel: TextView = itemView.findViewById(R.id.headerCourseLevelText)
        var courseTime: TextView = itemView.findViewById(R.id.headerCourseTimeText)
        var courseParticipants: TextView = itemView.findViewById(R.id.headerCourseParticipantsText)
        var headerCourseImage: ImageView = itemView.findViewById(R.id.headerCourseImageItem)
        var headerCourseButton : RelativeLayout = itemView.findViewById(R.id.headerCourseDetailBtn)


        //var course_detail_btn: RelativeLayout = itemView.findViewById(R.id.courseDetailBtn)

    }

    inner class LetterCourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var letterTitle: TextView = itemView.findViewById(R.id.letterTitleItem)
        var letterSubTitle: TextView = itemView.findViewById(R.id.letterSubTitleItem)
        var letterHint: TextView = itemView.findViewById(R.id.letterHint)
        var letterButton: RelativeLayout = itemView.findViewById(R.id.letterDetailBtn)
        var letterImage: ImageView = itemView.findViewById(R.id.letterImageItem)

    }
}