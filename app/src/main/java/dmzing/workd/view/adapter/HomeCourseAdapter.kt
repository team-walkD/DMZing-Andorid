package dmzing.workd.view.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.text.method.ScrollingMovementMethod
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
import dmzing.workd.dialog.HomeLetterFindDialog
import dmzing.workd.dialog.HomeLetterXDialog
import dmzing.workd.model.Test
import dmzing.workd.model.home.HomePostMission
import dmzing.workd.model.home.PickCourse
import dmzing.workd.model.home.Places
import dmzing.workd.model.map.CourseDetailDto
import dmzing.workd.network.ApplicationController
import dmzing.workd.network.NetworkService
import dmzing.workd.util.SharedPreference
import dmzing.workd.view.home.HomeLetterActivity
import dmzing.workd.view.map.CourseMainActivity
import dmzing.workd.view.map.MapFragment
import dmzing.workd.view.mypage.MypageFragment
import kotlinx.android.synthetic.main.home_letter_item_list.view.*
import org.jetbrains.anko.startActivity
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
    private val TYPE_FOOTER = 5
    private var count = 0

    companion object {
        var index: Int = 0
        var idx: Int = 0
        var common_position = 0
        var footer_flag: Int = 0
        var letterFlag: Int = 0
        var location_list = listOf<Test>(
            Test(37.8895234711, 126.7405308247)
            , Test(37.8513232698, 126.7905662159)
            , Test(37.7773633354, 126.684436368)
            , Test(37.7689256453, 126.6964910575)
        )
    }

    // 리사이클러뷰 생명주기
    // 붙을 때 아이템이 추가된것을 알려줌.
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        notifyItemInserted(item_list.places.size + 1)
    }


    fun setOnItemClick(l: View.OnClickListener) {
        onItemClick = l
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_HEADER) {
            var headerView = LayoutInflater.from(parent.context)
                .inflate(R.layout.home_header_course_item_list, parent, false)
            return HeaderCourseViewHolder(headerView)
        } else if (viewType == TYPE_LETTER) {
            var letterView = LayoutInflater.from(parent!!.context)
                .inflate(R.layout.home_letter_item_list, parent, false)
            letterView.setOnClickListener(onItemClick)
            return LetterCourseViewHolder(letterView)
        } else {

            var footerView = LayoutInflater.from(parent.context).inflate(R.layout.home_last_item_list, parent, false)

            if (footer_flag == 100) {
                Log.v("1212 100: ", "footer call")
                footerView.setOnClickListener(onItemClick)
                footerView.visibility = View.VISIBLE
            } else {
                Log.v("1212 else: ", "footer call")
                footerView.setOnClickListener(onItemClick)
                footerView.visibility = View.GONE
            }

            if (item_list.places.size == 4) {
                Log.v("1212 size4: ", "footer call")
                footerView.setOnClickListener(onItemClick)
                footerView.visibility = View.VISIBLE
            }
            return FooterCourseViewHolder(footerView)

        }
    }

    override fun getItemViewType(position: Int): Int {
        if (isPositionHeader(position))
            return TYPE_HEADER
        else if (isPostionFooter(position)) {
            Log.v("1212 call: ", "footer call")
            return TYPE_FOOTER
        } else {
            return TYPE_LETTER
        }

    }

    fun isPositionHeader(position: Int): Boolean {
        return position == TYPE_HEADER
    }

    fun isPositionLetter(position: Int): Boolean {
        return position == TYPE_LETTER
    }

    fun isPostionFooter(position: Int): Boolean {
        var num: Int = item_list.places.size
        Log.v("1212 : ", "position : ${position}, num : ${num}")
        return position == 5
        /*item_list.places.size + 1*/
    }

    override fun getItemCount(): Int {
        Log.v("1252 size ", item_list.places.size.toString())
        //notifyDataSetChanged()
        if (footer_flag == 100) {
            Log.v("1252 if", footer_flag.toString())
            //notifyDataSetChanged()
            return item_list.places.size + 2
            Log.v("12522 if", footer_flag.toString())


        } else {
            if (CommonData.complete_flag == 4) {
                Log.v("1252 else", footer_flag.toString())
                return item_list.places.size + 2
            } else {
                Log.v("1252 else", footer_flag.toString())
                return item_list.places.size + 1
            }
            notifyDataSetChanged()
        }
        //notifyDataSetChanged()

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.v("304 bind position:",position.toString())
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
                getCourseDetail(item_list.id, SharedPreference.instance!!.getPrefStringData("jwt")!!)
            }

        } else if (holder is LetterCourseViewHolder) {
            var letterCourseViewHolder: LetterCourseViewHolder = holder
            letterCourseViewHolder.letterTitle.text = item_list.places[position-1].title
            //letterCourseViewHolder.letterSubTitle.text = item_list.places[position-1].description

            // 플레이스 아이디 저장
            CommonData.placeId = item_list.places[position-1].id
            Glide.with(context).load(item_list.places[position-1].mainImageUrl)
                .into(letterCourseViewHolder.letterImage)
            letterCourseViewHolder.letterHint.movementMethod = ScrollingMovementMethod.getInstance()
            letterCourseViewHolder.letterHint.text = item_list.places[position-1].hint
            if (item_list.places[position-1].letterImageUrl != null) {
                letterCourseViewHolder.letterDetailText.text = "편지 보기"
                letterFlag = 1
            } else {
                letterCourseViewHolder.letterDetailText.text = "편지 찾기"
                letterFlag = 2
            }
            letterCourseViewHolder.letterButton.setOnClickListener {
                when (letterFlag) {
                    1 -> {
                        //context.toast("편지 보기 버튼!")
                        var image: String = item_list.places[position-1].letterImageUrl!!
                        context!!.startActivity<HomeLetterActivity>("letter" to image)
                    }
                    2 -> {
                        //context.toast("편지 찾기 버튼!")

                        postMission(CommonData.commonLatitude,CommonData.commonLongitude)
                        //postMission(location_list[idx].lat, location_list[idx].lng)
                        Log.v("557 lat", location_list[idx].lat.toString())
                        Log.v("557 lng", location_list[idx].lng.toString())
                    }
                }


            }

        } else if (holder is FooterCourseViewHolder) {
            var footerCourseViewHolder: FooterCourseViewHolder = holder

        }


    }

    fun getCourseDetail(cid: Int, jwt: String) {
        networkService = ApplicationController.instance.networkService
        val getCourseDetailResponse = networkService.getCourseDetail(jwt, cid)
        getCourseDetailResponse.enqueue(object : Callback<CourseDetailDto> {
            override fun onFailure(call: Call<CourseDetailDto>, t: Throwable) {

            }

            override fun onResponse(call: Call<CourseDetailDto>, response: Response<CourseDetailDto>) {
                when (response.code()) {
                    200 -> {
                        var intent = Intent(context, CourseMainActivity::class.java)
                        intent.putExtra("courseDetailDto", response.body())
                        context!!.startActivity(intent)
                    }
                    401 -> {

                    }
                    500 -> {

                    }
                }
            }

        })
    }

    // 편지 찾기 post 통신/
    /*FIXME
    * 편지 찾고 아이템 받아서 갱신하는 로직 넣음.
    * */
    fun postMission(lat: Double?, lot: Double?) {
        networkService = ApplicationController.instance.networkService
        SharedPreference.instance!!.load(context)
        homePostMission = HomePostMission(CommonData.coursedId, CommonData.placeId, lat, lot)
        var jwt = SharedPreference.instance!!.getPrefStringData(CommonData.JWT)
        var missionResponse = networkService.postMission(jwt!!, homePostMission)
        missionResponse.enqueue(object : Callback<ArrayList<Places>> {
            override fun onFailure(call: Call<ArrayList<Places>>, t: Throwable) {
                Log.v("110 woo f:", t.message)
            }

            override fun onResponse(call: Call<ArrayList<Places>>, response: Response<ArrayList<Places>>) {
                Log.v("110 woo r:", response.code().toString())
                Log.v("110 woo r:", response.body().toString())
                when (response.code()) {
                    200 -> {
                        var tmp = response.body()!!
                        var lastObj: ArrayList<Places>
                        var position = item_list.places.size
                        var sequence = item_list.places[count].sequence



                        if (tmp[1].sequence == 100 && tmp[1].letterImageUrl != null) {
                            //context.toast("그만 추가하시죠")
                            Log.v("1244 size:", item_list.places.size.toString())
                            common_position = item_list.places.size
                            footer_flag = tmp[1].sequence
                            Log.v("1244 flag", footer_flag.toString())
                            notifyItemInserted(item_list.places.size + 1)
                            itemCount
                        } else {
                            index = position

                            Log.v("557 count:", count.toString())
                            Log.v("557 seq:", sequence.toString())
                            Log.v("557 tmp:", tmp.toString())
                            //Log.v("557 seq", tmp[sequence].sequence.toString())

                            item_list.places.add(tmp[1])
                            count += 1
                            idx += 1
                            //notifyDataSetChanged()
                            notifyItemInserted(position + 1)


                        }
                        var dialog = HomeLetterFindDialog(context)
                        dialog.setCanceledOnTouchOutside(true)
                        CommonData.complete_flag += 1
                        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                        dialog.show()


                    }
                    400 -> {
                        var dialog = HomeLetterXDialog(context, 0)
                        dialog.setCanceledOnTouchOutside(true)
                        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                        dialog.show()
                    }
                    else -> {

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
        var headerCourseButton: RelativeLayout = itemView.findViewById(R.id.headerCourseDetailBtn)
    }

    inner class LetterCourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var letterTitle: TextView = itemView.findViewById(R.id.letterTitleItem)
        var letterSubTitle: TextView = itemView.findViewById(R.id.letterSubTitleItem)
        var letterHint: TextView = itemView.findViewById(R.id.letterHint)
        var letterButton: RelativeLayout = itemView.findViewById(R.id.letterDetailBtn)
        var letterImage: ImageView = itemView.findViewById(R.id.letterImageItem)
        var letterDetailText: TextView = itemView.letterDetailBtnText
    }

    inner class FooterCourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}